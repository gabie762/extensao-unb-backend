package extensao.backend.controller;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import extensao.backend.dto.usuarios.UsuarioResponseDTO;
import extensao.backend.dto.usuarios.UsuarioCreateDTO;
import extensao.backend.dto.usuarios.UsuarioUpdateDTO;
import extensao.backend.service.UsuarioService;
import extensao.backend.entity.Usuario;
import extensao.backend.mapper.UsuarioMapper;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController{

    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listUsuarios() {
        List<Usuario> usuariosDoBanco = usuarioService.listAll();

        List<UsuarioResponseDTO> respostaDTOs = usuariosDoBanco.stream()
            .map(usuario -> UsuarioMapper.toResponse(usuario))
            .collect(Collectors.toList());

        return ResponseEntity.ok(respostaDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable String id) {
        return usuarioService.getById(id)
        .map(usuarioEncontrado -> ResponseEntity.ok(UsuarioMapper.toResponse(usuarioEncontrado)))
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> createUsuario(@Valid @RequestBody UsuarioCreateDTO dto) {
        Usuario usuarioParaSalvar = UsuarioMapper.toEntity(dto);
        Usuario usuarioSalvo = this.usuarioService.create(usuarioParaSalvar);
        UsuarioResponseDTO respostaDTO = UsuarioMapper.toResponse(usuarioSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(respostaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(@PathVariable String id, @Valid @RequestBody UsuarioUpdateDTO dto) {
        Usuario usuarioExistente = this.usuarioService.getById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado!"));

        UsuarioMapper.updateEntityFromDto(dto, usuarioExistente);

        // save fará encode da senha se fornecida
        Usuario usuarioAtualizado = this.usuarioService.save(usuarioExistente);
        UsuarioResponseDTO respostaDTO = UsuarioMapper.toResponse(usuarioAtualizado);
        return ResponseEntity.ok(respostaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable String id){
        this.usuarioService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> findByEmail(@PathVariable String email){
        return usuarioService.findByEmail(email)
        .map(usuarioByEmail -> ResponseEntity.ok(UsuarioMapper.toResponse(usuarioByEmail)))
        .orElse(ResponseEntity.notFound().build());
    }
}
