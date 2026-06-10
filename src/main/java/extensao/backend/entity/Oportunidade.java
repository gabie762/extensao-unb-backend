package extensao.backend.entity;

import java.time.Instant;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "oportunidades")
@Getter
@Setter
@NoArgsConstructor
public class Oportunidade {

    @Id
    private String id;
    private String projetoId;
    private String titulo;
    private String descricao;
    private String sobreProjeto;
    private List<String> objetivos;
    private List<String> atividadesDesenvolvidas;
    private List<String> comoParticipar;
    private boolean certificado;
    private List<String> requisitos;
    private Integer qtdeVagas;
    private String prazoInscricao;
    private String tipo; // ex: "bolsa", "voluntariado"
    private String local;
    private String cargaHoraria;
    private String status;

    @CreatedDate
    private Instant criadoEm;

    @LastModifiedDate
    private Instant atualizadoEm;
}
