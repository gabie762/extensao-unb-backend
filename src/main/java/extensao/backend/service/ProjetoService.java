package extensao.backend.service;

import java.util.List;

import extensao.backend.entity.Projeto;
import java.util.Optional;

public interface ProjetoService {
    List<Projeto> listarTodos();
    Optional<Projeto> buscarPorId(String id);
    Projeto criar(Projeto projeto);
    Projeto atualizar(String id, Projeto projeto);
    void deletar(String id);
}
