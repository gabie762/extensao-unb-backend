package extensao.backend.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponseDTO {
    
    @NotBlank
    private String token;
}
