package extensao.backend.dto.projetos;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjetoRequestDTO {
    @NotBlank
    private String titulo;

    @NotBlank
    private String area;

    @NotBlank
    private String unidadeResponsavel;

    @NotBlank
    private String resumo;

    @NotBlank
    private String coordenador;

    private String cronograma;

    private List<String> tags;

    private String status;

    private Integer vagas;

    private ProximoEventoDTO proximoEvento;

    @Getter
    @Setter
    public static class ProximoEventoDTO {
        private String titulo;
        private String dataInicio;
    }
}
