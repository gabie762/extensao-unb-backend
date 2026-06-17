package extensao.backend.dto.oportunidades;

import java.time.Instant;
import java.util.List;
import extensao.backend.dto.projetos.ProjetoResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OportunidadeResponseDTO {
    private String id;
    private String projetoId;
    private ProjetoResponseDTO projeto;
    private String titulo;
    private String descricao;
    private String sobreProjeto;
    private List<String> objetivos;
    private List<String> atividadesDesenvolvidas;
    private List<String> comoParticipar;
    private boolean certificado;
    private List<String> requisitos;
    private Integer vagas;
    private String prazoInscricao;
    private String tipo;
    private String local;
    private String cargaHoraria;
    private String status;
    private Instant criadoEm;
    private Instant atualizadoEm;
}
