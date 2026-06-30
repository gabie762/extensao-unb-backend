package extensao.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import extensao.backend.dto.usuarios.UsuarioResponseDTO;
import extensao.backend.dto.usuarios.UsuarioCreateDTO;
import extensao.backend.dto.usuarios.UsuarioUpdateDTO;
import extensao.backend.service.UsuarioService;
import extensao.backend.entity.Usuario;
import extensao.backend.mapper.UsuarioMapper;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listUsuarios() {
        List<Usuario> usuariosDoBanco = usuarioService.listAll();
        List<UsuarioResponseDTO> respostaDTOs = usuariosDoBanco.stream()
            .map(UsuarioMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(respostaDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable String id) {
        return usuarioService.getById(id)
            .map(u -> ResponseEntity.ok(UsuarioMapper.toResponse(u)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> findByEmail(@PathVariable String email) {
        return usuarioService.findByEmail(email)
            .map(u -> ResponseEntity.ok(UsuarioMapper.toResponse(u)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> createUsuario(@Valid @RequestBody UsuarioCreateDTO dto) {
        Usuario usuarioParaSalvar = UsuarioMapper.toEntity(dto);
        Usuario usuarioSalvo = usuarioService.create(usuarioParaSalvar);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toResponse(usuarioSalvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(
            @PathVariable String id,
            @Valid @RequestBody UsuarioUpdateDTO dto,
            Authentication authentication) {

        Usuario alvo = usuarioService.getById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));

        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !alvo.getEmail().equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão para editar este usuário");
        }

        UsuarioMapper.updateEntityFromDto(dto, alvo);

        // Codifica a nova senha apenas se fornecida — evita double-encoding do hash existente
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            alvo.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        return ResponseEntity.ok(UsuarioMapper.toResponse(usuarioService.save(alvo)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable String id, Authentication authentication) {
        Usuario alvo = usuarioService.getById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));

        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !alvo.getEmail().equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão para deletar este usuário");
        }

        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/papeis")
    public ResponseEntity<UsuarioResponseDTO> updatePapeis(
            @PathVariable String id,
            @RequestBody List<String> papeis) {
        return ResponseEntity.ok(UsuarioMapper.toResponse(usuarioService.updatePapeis(id, papeis)));
    }
}
