package extensao.backend.dto.eventos;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventoResponseDTO {
    private String id;
    private String projetoId;
    private String titulo;
    private Instant inicioEm;
    private Instant fimEm;
    private String local;
    private String tipo;
}
