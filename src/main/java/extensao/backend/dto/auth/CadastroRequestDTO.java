package extensao.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroRequestDTO {

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    @Pattern(regexp = ".*@(.+\\.)?unb\\.br$", message = "O e-mail deve pertencer ao domínio unb.br")
    private String email;

    @NotBlank
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    private String senha;

    @NotNull
    @Pattern(regexp = "ROLE_STUDENT|ROLE_PROFESSOR", message = "O papel deve ser ROLE_STUDENT ou ROLE_PROFESSOR")
    private String role;
}
