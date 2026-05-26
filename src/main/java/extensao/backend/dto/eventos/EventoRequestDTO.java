package extensao.backend.dto.eventos;

import java.time.Instant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventoRequestDTO {
    @NotBlank
    private String projetoId;
    @NotBlank
    private String titulo;
    @NotNull
    private Instant inicioEm;
    private Instant fimEm;
    private String local;
    private String tipo;
}
