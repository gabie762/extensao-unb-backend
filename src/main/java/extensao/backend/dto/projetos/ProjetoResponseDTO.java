package extensao.backend.dto.projetos;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjetoResponseDTO {
    private String id;
    private String titulo;
    private String area;
    private String unidadeResponsavel;
    private String resumo;
    private String coordenador;
    private String cronograma;
    private List<String> tags;
    private String status;
    private Integer quantidadeParticipantes;
    private ProximoEventoDTO proximoEvento;

    @Getter
    @Setter
    public static class ProximoEventoDTO {
        private String titulo;
        private String dataInicio;
    }
}
