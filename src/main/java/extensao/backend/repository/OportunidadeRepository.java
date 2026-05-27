package extensao.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import extensao.backend.entity.Oportunidade;
import java.util.List;

@Repository
public interface OportunidadeRepository extends MongoRepository<Oportunidade, String> {
    List<Oportunidade> findByProjetoId(String projetoId);
}
