package extensao.backend.dto.usuarios;

import java.util.List;

import java.time.Instant;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class UsuarioResponseDTO {
    private String id;
    private String nome;
    private String email;
    private List<String> papeis;
    private String unidade;
    private String semestre;
    private List<String> interesses;
    private String bio;
    private Instant criadoEm;
    private boolean ativo;
}