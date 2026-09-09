package extensao.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import extensao.backend.dto.auth.CadastroRequestDTO;
import extensao.backend.dto.auth.CadastroResponseDTO;
import extensao.backend.dto.auth.LoginRequestDTO;
import extensao.backend.dto.auth.MensagemResponseDTO;
import extensao.backend.dto.auth.ReenviarVerificacaoRequestDTO;
import extensao.backend.dto.auth.TokenResponseDTO;
import extensao.backend.dto.auth.VerificarEmailRequestDTO;
import extensao.backend.dto.usuarios.UsuarioResponseDTO;
import extensao.backend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/cadastro")
    public ResponseEntity<CadastroResponseDTO> cadastro(@Valid @RequestBody CadastroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.cadastro(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO requestDTO,
            HttpServletRequest request) {
        String ip = resolverIp(request);
        return ResponseEntity.ok(authService.login(requestDTO, ip));
    }

    @PostMapping("/verificar-email")
    public ResponseEntity<TokenResponseDTO> verificarEmail(@Valid @RequestBody VerificarEmailRequestDTO dto) {
        return ResponseEntity.ok(authService.verificarEmail(dto.getToken()));
    }

    @PostMapping("/reenviar-verificacao")
    public ResponseEntity<MensagemResponseDTO> reenviarVerificacao(@Valid @RequestBody ReenviarVerificacaoRequestDTO dto) {
        return ResponseEntity.ok(authService.reenviarVerificacao(dto.getEmail()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> me(Authentication authentication) {
        return ResponseEntity.ok(authService.me(authentication.getName()));
    }

    private String resolverIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
