package extensao.backend.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import extensao.backend.dto.projetos.ProjetoRequestDTO;
import extensao.backend.dto.projetos.ProjetoResponseDTO;
import extensao.backend.entity.Projeto;
import extensao.backend.mapper.ProjetoMapper;
import extensao.backend.service.ProjetoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @Value("${import.api-key}")
    private String importApiKey;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<ProjetoResponseDTO>> listarProjetos(){
        List<Projeto> projetosDoBanco = projetoService.listarTodos();
        
        List<ProjetoResponseDTO> respostaDTOs = projetosDoBanco.stream()
            .map(projeto -> ProjetoMapper.toResponse(projeto))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(respostaDTOs);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> getProjetoById(@PathVariable String id){
        return projetoService.buscarPorId(id)
            .map(projetoEncontrado -> ResponseEntity.ok(ProjetoMapper.toResponse(projetoEncontrado)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProjetoResponseDTO> criarProjeto(@Valid @RequestBody ProjetoRequestDTO requestDTO){
        Projeto projetoParaSalvar = ProjetoMapper.toEntity(requestDTO);

        Projeto projetoSalvo = projetoService.criar(projetoParaSalvar);

        ProjetoResponseDTO respostaDTO = ProjetoMapper.toResponse(projetoSalvo);
    
        return ResponseEntity.status(HttpStatus.CREATED).body(respostaDTO);
    }

    /**
     * Endpoint de importacao em massa, para scripts externos (ex: dados raspados do SIGAA).
     * Nao usa login: em vez disso, exige o header "X-Import-Key" com o valor configurado
     * em IMPORT_API_KEY. Ver SecurityConfig - esta rota e liberada do JWT explicitamente.
     */
    @PostMapping("/importar")
    public ResponseEntity<ProjetoResponseDTO> importarProjeto(
        @RequestHeader(value = "X-Import-Key", required = false) String chaveRecebida,
        @Valid @RequestBody ProjetoRequestDTO requestDTO
    ){
        if (!chaveDeImportacaoValida(chaveRecebida)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Projeto projetoParaSalvar = ProjetoMapper.toEntity(requestDTO);

        Projeto projetoSalvo = projetoService.criar(projetoParaSalvar);

        ProjetoResponseDTO respostaDTO = ProjetoMapper.toResponse(projetoSalvo);

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaDTO);
    }

    private boolean chaveDeImportacaoValida(String chaveRecebida) {
        if (importApiKey == null || importApiKey.isBlank() || chaveRecebida == null) {
            return false;
        }
        byte[] esperada = importApiKey.getBytes(StandardCharsets.UTF_8);
        byte[] recebida = chaveRecebida.getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual(esperada, recebida);
    }

    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> updateProjeto(
        @PathVariable String id, @Valid @RequestBody ProjetoRequestDTO requestDTO){
        Projeto projetoDadosNovos = ProjetoMapper.toEntity(requestDTO);

        Projeto projetoAtualizado = projetoService.atualizar(id, projetoDadosNovos);
        
        return ResponseEntity.ok(ProjetoMapper.toResponse(projetoAtualizado));
    }

    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProjeto(@PathVariable String id){
        this.projetoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
