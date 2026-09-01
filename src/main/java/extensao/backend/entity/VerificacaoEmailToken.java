package extensao.backend.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "verificacao_email_tokens")
@Getter
@Setter
public class VerificacaoEmailToken {

    @Id
    private String id;

    private String usuarioId;

    private String tokenHash;

    // TTL index: o Mongo remove o documento sozinho quando expiraEm é atingido
    @Indexed(expireAfterSeconds = 0)
    private Instant expiraEm;

    private boolean usado;

    private Instant criadoEm = Instant.now();
}
