package extensao.backend.dto.projetos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Usado por POST /projetos/importar. Ao contrario de ProjetoRequestDTO, nao exige
 * o id de um usuario coordenador ja cadastrado - basta nome e e-mail. Se ainda nao
 * existir uma conta com esse e-mail, uma conta "placeholder" e criada automaticamente
 * (ver UsuarioService.resolverOuCriarCoordenadorPlaceholder), que a pessoa pode
 * reivindicar depois se cadastrando normalmente com o mesmo e-mail.
 */
@Getter
@Setter
public class ProjetoImportRequestDTO {
    @NotBlank
    private String titulo;

    @NotBlank
    private String area;

    @NotBlank
    private String unidadeResponsavel;

    @NotBlank
    private String resumo;

    @NotBlank
    private String coordenadorNome;

    @NotBlank
    @Email
    private String coordenadorEmail;

    private String cronograma;

    private String status;

    private Integer vagas;

    private ProjetoRequestDTO.ProximoEventoDTO proximoEvento;
}
