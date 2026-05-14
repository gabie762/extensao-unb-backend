package extensao.backend.dto.usuarios;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {
    @NotBlank
    private String nome;
    
    @Email
    @NotBlank
    private String email;
    
    // senha é obrigatória apenas na criação; permitimos null/ausente para updates
    @Size(min = 6)
    private String senha;

    private List<String> papeis;

    private String unidade;

    private String semestre;

    private List<String> interesses = new ArrayList<>();

    private String bio;

    private Boolean ativo;

}