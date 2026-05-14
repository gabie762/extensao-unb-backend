package extensao.backend.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Document(collection = "usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    private String id;

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    @Indexed(unique = true)
    private String email;

    @Size(min = 1)
    private List<String> papeis = new ArrayList<>();

    @NotBlank
    private String unidade;

    private String semestre;

    private List<String> interesses = new ArrayList<>();

    private String bio;

    @CreatedDate
    private Instant criadoEm = Instant.now();

    private boolean ativo;

    @NotBlank
    @Size(min = 6)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;


    public Usuario() {
    }

    public Usuario(String id, String nome, String email, String senha, List<String> papeis, Instant criadoEm) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.papeis = papeis;
        this.criadoEm = criadoEm == null ? Instant.now() : criadoEm;
    }

}
