package extensao.backend.controller;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import extensao.backend.dto.eventos.EventoRequestDTO;
import extensao.backend.dto.eventos.EventoResponseDTO;
import extensao.backend.entity.Evento;
import extensao.backend.mapper.EventoMapper;
import extensao.backend.service.EventoService;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService service;

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos().stream()
                .map(EventoMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id)
                .map(e -> ResponseEntity.ok(EventoMapper.toResponse(e)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<EventoResponseDTO>> listarPorProjeto(@PathVariable String projetoId) {
        return ResponseEntity.ok(service.listarPorProjeto(projetoId).stream()
                .map(EventoMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EventoResponseDTO> criar(@Valid @RequestBody EventoRequestDTO dto) {
        Evento evento = EventoMapper.toEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(EventoMapper.toResponse(service.criar(evento)));
    }

    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> atualizar(@PathVariable String id, @Valid @RequestBody EventoRequestDTO dto) {
        Evento dadosNovos = EventoMapper.toEntity(dto);
        return ResponseEntity.ok(EventoMapper.toResponse(service.atualizar(id, dadosNovos)));
    }

    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
