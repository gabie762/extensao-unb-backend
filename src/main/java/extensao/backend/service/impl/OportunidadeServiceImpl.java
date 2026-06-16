package extensao.backend.service.impl;

import extensao.backend.entity.Oportunidade;
import extensao.backend.repository.OportunidadeRepository;
import extensao.backend.repository.ProjetoRepository;
import extensao.backend.service.OportunidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class OportunidadeServiceImpl implements OportunidadeService {

    @Autowired
    private OportunidadeRepository repository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Override
    public List<Oportunidade> listarTodas() {
        return repository.findAll();
    }

    @Override
    public Optional<Oportunidade> buscarPorId(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Oportunidade> listarPorProjeto(String projetoId) {
        return repository.findByProjetoId(projetoId);
    }

    @Override
    public Oportunidade criar(Oportunidade oportunidade) {
        if (oportunidade.getStatus() == null) {
            oportunidade.setStatus("Aberta");
        }
        
        if (oportunidade.getProjetoId() != null) {
            oportunidade.setProjeto(projetoRepository.findById(oportunidade.getProjetoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Projeto não encontrado com o ID: " + oportunidade.getProjetoId())));
        }

        return repository.save(oportunidade);
    }

    @Override
    public Oportunidade atualizar(String id, Oportunidade dadosNovos) {
        Oportunidade oportunidade = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Oportunidade não encontrada"));
        
        oportunidade.setProjetoId(dadosNovos.getProjetoId());
        
        if (dadosNovos.getProjetoId() != null) {
            oportunidade.setProjeto(projetoRepository.findById(dadosNovos.getProjetoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Projeto não encontrado com o ID: " + dadosNovos.getProjetoId())));
        } else {
            oportunidade.setProjeto(null);
        }

        oportunidade.setTitulo(dadosNovos.getTitulo());
        oportunidade.setDescricao(dadosNovos.getDescricao());
        oportunidade.setSobreProjeto(dadosNovos.getSobreProjeto());
        oportunidade.setObjetivos(dadosNovos.getObjetivos());
        oportunidade.setAtividadesDesenvolvidas(dadosNovos.getAtividadesDesenvolvidas());
        oportunidade.setComoParticipar(dadosNovos.getComoParticipar());
        oportunidade.setCertificado(dadosNovos.isCertificado());
        oportunidade.setRequisitos(dadosNovos.getRequisitos());
        oportunidade.setVagas(dadosNovos.getVagas());
        oportunidade.setPrazoInscricao(dadosNovos.getPrazoInscricao());
        oportunidade.setTipo(dadosNovos.getTipo());
        oportunidade.setLocal(dadosNovos.getLocal());
        oportunidade.setCargaHoraria(dadosNovos.getCargaHoraria());
        oportunidade.setStatus(dadosNovos.getStatus());

        return repository.save(oportunidade);
    }

    @Override
    public void deletar(String id) {
        repository.deleteById(id);
    }
}
