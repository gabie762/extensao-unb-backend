package extensao.backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import extensao.backend.entity.VerificacaoEmailToken;

@Repository
public interface VerificacaoEmailTokenRepository extends MongoRepository<VerificacaoEmailToken, String> {
    Optional<VerificacaoEmailToken> findByTokenHash(String tokenHash);
}
