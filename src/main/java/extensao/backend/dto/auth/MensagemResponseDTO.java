package extensao.backend.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemResponseDTO {

    private String mensagem;

    public MensagemResponseDTO() {
    }

    public MensagemResponseDTO(String mensagem) {
        this.mensagem = mensagem;
    }
}
