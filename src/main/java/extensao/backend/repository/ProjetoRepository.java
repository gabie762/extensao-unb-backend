package extensao.backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import extensao.backend.entity.Projeto;

@Repository
public interface ProjetoRepository extends MongoRepository<Projeto, String>{
    Optional<Projeto> findByTitulo(String titulo);

}
