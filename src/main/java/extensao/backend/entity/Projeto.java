package extensao.backend.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "projetos")
@Getter
@Setter
@NoArgsConstructor
public class Projeto {

    @Id
    private String id;
    private String titulo;
    private String area;
    private String unidadeResponsavel;
    private String resumo;
    @Indexed
    private String coordenadorId;
    private Usuario coordenador;
    private String cronograma;
    private List<String> tags;
    private String status;
    private Integer vagas;
    private ProximoEvento proximoEvento;

    @Getter
    @Setter
    public static class ProximoEvento{
        private String titulo;
        private String dataInicio;
    }
}