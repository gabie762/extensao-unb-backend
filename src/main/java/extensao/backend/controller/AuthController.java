package extensao.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import extensao.backend.dto.auth.LoginRequestDTO;
import extensao.backend.dto.auth.TokenResponseDTO;
import extensao.backend.service.AuthService;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(LoginRequestDTO requestDTO){
        TokenResponseDTO token = authService.login(requestDTO);
        return ResponseEntity.ok(token);
    }
}
