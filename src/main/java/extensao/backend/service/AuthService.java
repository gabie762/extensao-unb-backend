package extensao.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import extensao.backend.dto.auth.LoginRequestDTO;
import extensao.backend.dto.auth.TokenResponseDTO;
import extensao.backend.dto.usuarios.UsuarioResponseDTO;
import extensao.backend.entity.Usuario;
import extensao.backend.mapper.UsuarioMapper;
import extensao.backend.security.JwtService;
import extensao.backend.security.LoginAttemptService;
import extensao.backend.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private LoginAttemptService loginAttemptService;

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

        loginAttemptService.registrarSucesso(ip);

        String token = jwtService.generateToken(usuario.getEmail());

        TokenResponseDTO response = new TokenResponseDTO();
        response.setToken(token);
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setPapeis(usuario.getPapeis());

        return response;
    }

    public UsuarioResponseDTO me(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        return UsuarioMapper.toResponse(usuario);
    }
}
