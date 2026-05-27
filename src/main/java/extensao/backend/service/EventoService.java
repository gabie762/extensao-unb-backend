package extensao.backend.service;

import extensao.backend.entity.Evento;
import java.util.List;
import java.util.Optional;

public interface EventoService {
    List<Evento> listarTodos();
    Optional<Evento> buscarPorId(String id);
    List<Evento> listarPorProjeto(String projetoId);
    Evento criar(Evento evento);
    Evento atualizar(String id, Evento evento);
    void deletar(String id);
}
