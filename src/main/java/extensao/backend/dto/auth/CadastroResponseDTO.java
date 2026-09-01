package extensao.backend.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroResponseDTO {

    private String mensagem;
    private String email;

    public CadastroResponseDTO() {
    }

    public CadastroResponseDTO(String mensagem, String email) {
        this.mensagem = mensagem;
        this.email = email;
    }
}
