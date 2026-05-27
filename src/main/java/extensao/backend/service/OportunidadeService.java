package extensao.backend.service;

import extensao.backend.entity.Oportunidade;
import java.util.List;
import java.util.Optional;

public interface OportunidadeService {
    List<Oportunidade> listarTodas();
    Optional<Oportunidade> buscarPorId(String id);
    List<Oportunidade> listarPorProjeto(String projetoId);
    Oportunidade criar(Oportunidade oportunidade);
    Oportunidade atualizar(String id, Oportunidade oportunidade);
    void deletar(String id);
}
