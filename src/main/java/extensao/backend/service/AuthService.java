package extensao.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import extensao.backend.dto.auth.LoginRequestDTO;
import extensao.backend.dto.auth.TokenResponseDTO;
import extensao.backend.entity.Usuario;
import extensao.backend.security.JwtService;
import extensao.backend.repository.UsuarioRepository;

public class AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public TokenResponseDTO login(LoginRequestDTO dto){
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
        .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));
        
        if(!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())){
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtService.generateToken(usuario.getEmail());
        
        TokenResponseDTO response = new TokenResponseDTO();
        response.setToken(token);

        return response;
    }
}
