package extensao.backend.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Document(collection = "usuarios")
@Getter
@Setter
public class Usuario implements UserDetails{

    @Id
    private String id;

    @NotBlank
    private String nome;

    @NotBlank
    @Email
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        //transforma lista de strings (ex: "Professor") em objetos pro springframework
        return papeis.stream()
                    .map(papel -> 
                        new SimpleGrantedAuthority("ROLE_" + papel.toUpperCase()))
                    .collect(Collectors.toList());
    }

    @Override
    public String getPassword(){
        return senha;
    }

    @Override
    public String getUsername(){
        return email;
    }

    //TODO: mudar valores das funcoes conforme desenvolver auth
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return this.ativo;
    }


}
