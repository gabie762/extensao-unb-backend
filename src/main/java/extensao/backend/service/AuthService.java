package extensao.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import extensao.backend.dto.auth.CadastroRequestDTO;
import extensao.backend.dto.auth.CadastroResponseDTO;
import extensao.backend.dto.auth.LoginRequestDTO;
import extensao.backend.dto.auth.MensagemResponseDTO;
import extensao.backend.dto.auth.TokenResponseDTO;
import extensao.backend.dto.usuarios.UsuarioResponseDTO;
import extensao.backend.entity.Usuario;
import extensao.backend.entity.VerificacaoEmailToken;
import extensao.backend.mapper.UsuarioMapper;
import extensao.backend.repository.VerificacaoEmailTokenRepository;
import extensao.backend.security.JwtService;
import extensao.backend.security.LoginAttemptService;
import extensao.backend.repository.UsuarioRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private static final long TOKEN_VERIFICACAO_VALIDADE_HORAS = 24;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VerificacaoEmailTokenRepository verificacaoEmailTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private EmailService emailService;

    public TokenResponseDTO login(LoginRequestDTO dto, String ip) {
        if (loginAttemptService.estaBloqueado(ip)) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                    "Muitas tentativas de login. Tente novamente em 15 minutos.");
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(dto.getEmail());

        // Mesma mensagem para usuário inexistente e senha errada — evita enumeração de e-mails
        if (usuarioOpt.isEmpty() || !passwordEncoder.matches(dto.getSenha(), usuarioOpt.get().getSenha())) {
            loginAttemptService.registrarFalha(ip);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Conta inativa");
        }

        if (!usuario.isEmailVerificado()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "E-mail não verificado. Verifique sua caixa de entrada.");
        }

        loginAttemptService.registrarSucesso(ip);

        return gerarTokenResponse(usuario);
    }

    public CadastroResponseDTO cadastro(CadastroRequestDTO dto) {
        Optional<Usuario> existenteOpt = usuarioRepository.findByEmail(dto.getEmail());

        // Conta "placeholder" criada por uma importacao (ver ProjetoController /projetos/importar):
        // em vez de bloquear, deixa a pessoa reivindicar a conta - preenche nome/senha reais e
        // mantem o mesmo id, entao projetos ja vinculados a esse coordenador continuam corretos.
        if (existenteOpt.isPresent() && !existenteOpt.get().isContaImportada()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }

        String papel = dto.getRole().equals("ROLE_PROFESSOR") ? "Professor" : "Estudante";

        Usuario usuario = existenteOpt.orElseGet(Usuario::new);
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setPapeis(List.of(papel));
        usuario.setAtivo(true);
        if (usuario.getUnidade() == null) {
            usuario.setUnidade("");
        }
        usuario.setEmailVerificado(false);
        usuario.setContaImportada(false);

        usuarioRepository.save(usuario);

        enviarNovoTokenVerificacao(usuario);

        return new CadastroResponseDTO(
                "Cadastro realizado. Verifique seu e-mail para ativar sua conta.",
                usuario.getEmail());
    }

    public TokenResponseDTO verificarEmail(String tokenBruto) {
        String hash = hash(tokenBruto);

        VerificacaoEmailToken token = verificacaoEmailTokenRepository.findByTokenHash(hash)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido"));

        if (token.isUsado() || token.getExpiraEm().isBefore(Instant.now())) {
            throw new ResponseStatusException(HttpStatus.GONE, "Token inválido ou expirado. Solicite um novo e-mail de verificação.");
        }

        Usuario usuario = usuarioRepository.findById(token.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        usuario.setEmailVerificado(true);
        usuarioRepository.save(usuario);

        token.setUsado(true);
        verificacaoEmailTokenRepository.save(token);

        return gerarTokenResponse(usuario);
    }

    public MensagemResponseDTO reenviarVerificacao(String email) {
        String mensagemGenerica = "Se o e-mail estiver cadastrado e pendente de verificação, enviamos um novo link.";

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return new MensagemResponseDTO(mensagemGenerica);
        }

        Usuario usuario = usuarioOpt.get();
        if (usuario.isEmailVerificado()) {
            return new MensagemResponseDTO(mensagemGenerica);
        }

        String chaveLimite = "reenvio:" + email;
        if (loginAttemptService.estaBloqueado(chaveLimite)) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                    "Muitas solicitações de reenvio. Tente novamente em 15 minutos.");
        }
        loginAttemptService.registrarFalha(chaveLimite);

        enviarNovoTokenVerificacao(usuario);

        return new MensagemResponseDTO(mensagemGenerica);
    }

    public UsuarioResponseDTO me(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        return UsuarioMapper.toResponse(usuario);
    }

    private void enviarNovoTokenVerificacao(Usuario usuario) {
        String tokenBruto = gerarTokenBruto();

        VerificacaoEmailToken token = new VerificacaoEmailToken();
        token.setUsuarioId(usuario.getId());
        token.setTokenHash(hash(tokenBruto));
        token.setExpiraEm(Instant.now().plus(TOKEN_VERIFICACAO_VALIDADE_HORAS, ChronoUnit.HOURS));
        token.setUsado(false);
        verificacaoEmailTokenRepository.save(token);

        emailService.enviarVerificacao(usuario.getEmail(), usuario.getNome(), tokenBruto);
    }

    private TokenResponseDTO gerarTokenResponse(Usuario usuario) {
        String token = jwtService.generateToken(usuario.getEmail());

        TokenResponseDTO response = new TokenResponseDTO();
        response.setToken(token);
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setPapeis(usuario.getPapeis());

        return response;
    }

    private String gerarTokenBruto() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hash(String valor) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(valor.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 não disponível", e);
        }
    }
}
