package extensao.backend.controller;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import extensao.backend.dto.oportunidades.OportunidadeRequestDTO;
import extensao.backend.dto.oportunidades.OportunidadeResponseDTO;
import extensao.backend.entity.Oportunidade;
import extensao.backend.mapper.OportunidadeMapper;
import extensao.backend.service.OportunidadeService;

@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {

    @Autowired
    private OportunidadeService service;

    @GetMapping
    public ResponseEntity<List<OportunidadeResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas().stream()
                .map(OportunidadeMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OportunidadeResponseDTO> buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id)
                .map(o -> ResponseEntity.ok(OportunidadeMapper.toResponse(o)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<OportunidadeResponseDTO>> listarPorProjeto(@PathVariable String projetoId) {
        return ResponseEntity.ok(service.listarPorProjeto(projetoId).stream()
                .map(OportunidadeMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<OportunidadeResponseDTO> criar(@Valid @RequestBody OportunidadeRequestDTO dto) {
        Oportunidade o = OportunidadeMapper.toEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(OportunidadeMapper.toResponse(service.criar(o)));
    }

    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<OportunidadeResponseDTO> atualizar(@PathVariable String id, @Valid @RequestBody OportunidadeRequestDTO dto) {
        Oportunidade dadosNovos = OportunidadeMapper.toEntity(dto);
        return ResponseEntity.ok(OportunidadeMapper.toResponse(service.atualizar(id, dadosNovos)));
    }

    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
