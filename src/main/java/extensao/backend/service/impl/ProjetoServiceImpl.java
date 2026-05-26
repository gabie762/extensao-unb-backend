package extensao.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import extensao.backend.service.ProjetoService;
import extensao.backend.entity.Projeto;
import extensao.backend.repository.ProjetoRepository;

@Service
public class ProjetoServiceImpl implements ProjetoService {
    
    @Autowired
    private ProjetoRepository projetoRepository;

    @Override
    public List<Projeto> listarTodos(){
        return projetoRepository.findAll();
    }

    @Override
    public Optional<Projeto> buscarPorId(String id) {
        return projetoRepository.findById(id);
    }

    @Override
    public Projeto criar(Projeto projeto){
        projeto.setStatus("Aberto");
        return projetoRepository.save(projeto);
    }

    @Override
    public Projeto atualizar(String id, Projeto projeto){
        Projeto projetoExistente = this.projetoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado."));
    
        projetoExistente.setTitulo(projeto.getTitulo());
        projetoExistente.setArea(projeto.getArea());
        projetoExistente.setUnidadeResponsavel(projeto.getUnidadeResponsavel());
        projetoExistente.setResumo(projeto.getResumo());
        projetoExistente.setCoordenador(projeto.getCoordenador());
        projetoExistente.setCronograma(projeto.getCronograma());
        projetoExistente.setTags(projeto.getTags());
        projetoExistente.setStatus(projeto.getStatus());
        projetoExistente.setQuantidadeParticipantes(projeto.getQuantidadeParticipantes());
        projetoExistente.setProximoEvento(projeto.getProximoEvento());

        return projetoRepository.save(projetoExistente);
    }

    @Override
    public void deletar(String id){
        this.projetoRepository.deleteById(id);
    }
}
