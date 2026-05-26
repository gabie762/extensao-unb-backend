package extensao.backend.dto.oportunidades;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OportunidadeRequestDTO {
    @NotBlank
    private String projetoId;
    @NotBlank
    private String titulo;
    @NotBlank
    private String descricao;
    private String sobreProjeto;
    private List<String> objetivos;
    private List<String> atividadesDesenvolvidas;
    private List<String> comoParticipar;
    private boolean certificado;
    private List<String> requisitos;
    private String prazoInscricao;
    private String tipo;
    private String local;
    private String cargaHoraria;
    private String status;
}
