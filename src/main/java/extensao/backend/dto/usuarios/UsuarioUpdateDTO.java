package extensao.backend.dto.usuarios;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdateDTO {
    private String nome;

    @Email
    private String email;

    // senha opcional no update
    @Size(min = 6)
    private String senha;

    private List<String> papeis;

    private String unidade;

    private String semestre;

    private List<String> interesses;

    private String bio;

    private Boolean ativo;

}
