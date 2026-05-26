package extensao.backend.service.impl;

import extensao.backend.entity.Evento;
import extensao.backend.repository.EventoRepository;
import extensao.backend.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository repository;

    @Override
    public List<Evento> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Evento> buscarPorId(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Evento> listarPorProjeto(String projetoId) {
        return repository.findByProjetoId(projetoId);
    }

    @Override
    public Evento criar(Evento evento) {
        return repository.save(evento);
    }

    @Override
    public Evento atualizar(String id, Evento dadosNovos) {
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
        
        evento.setProjetoId(dadosNovos.getProjetoId());
        evento.setTitulo(dadosNovos.getTitulo());
        evento.setInicioEm(dadosNovos.getInicioEm());
        evento.setFimEm(dadosNovos.getFimEm());
        evento.setLocal(dadosNovos.getLocal());
        evento.setTipo(dadosNovos.getTipo());

        return repository.save(evento);
    }

    @Override
    public void deletar(String id) {
        repository.deleteById(id);
    }
}
