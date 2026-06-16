package extensao.backend.mapper;

import java.util.stream.Collectors;
import extensao.backend.entity.Projeto;
import extensao.backend.dto.projetos.ProjetoRequestDTO;
import extensao.backend.dto.projetos.ProjetoResponseDTO;

public class ProjetoMapper {

    public static Projeto toEntity(ProjetoRequestDTO dto) {
        Projeto projeto = new Projeto();
        projeto.setTitulo(dto.getTitulo());
        projeto.setArea(dto.getArea());
        projeto.setUnidadeResponsavel(dto.getUnidadeResponsavel());
        projeto.setResumo(dto.getResumo());
        projeto.setCoordenadorId(dto.getCoordenador()); // Armazena o ID/Nome enviado no request
        projeto.setCronograma(dto.getCronograma());
        projeto.setTags(dto.getTags());
        projeto.setStatus(dto.getStatus());
        projeto.setVagas(dto.getVagas());
        
        if (dto.getProximoEvento() != null) {
            Projeto.ProximoEvento evento = new Projeto.ProximoEvento();
            evento.setTitulo(dto.getProximoEvento().getTitulo());
            evento.setDataInicio(dto.getProximoEvento().getDataInicio());
            projeto.setProximoEvento(evento);
        }
        
        return projeto;
    }

    public static ProjetoResponseDTO toResponse(Projeto projeto) {
        ProjetoResponseDTO dto = new ProjetoResponseDTO();
        dto.setId(projeto.getId());
        dto.setTitulo(projeto.getTitulo());
        dto.setArea(projeto.getArea());
        dto.setUnidadeResponsavel(projeto.getUnidadeResponsavel());
        dto.setResumo(projeto.getResumo());
        
        if (projeto.getCoordenador() != null) {
            dto.setCoordenador(UsuarioMapper.toResponse(projeto.getCoordenador()));
        }
        
        dto.setCronograma(projeto.getCronograma());
        dto.setTags(projeto.getTags());
        dto.setStatus(projeto.getStatus());
        dto.setVagas(projeto.getVagas());
        
        if (projeto.getProximoEvento() != null) {
            ProjetoResponseDTO.ProximoEventoDTO eventoDto = new ProjetoResponseDTO.ProximoEventoDTO();
            eventoDto.setTitulo(projeto.getProximoEvento().getTitulo());
            eventoDto.setDataInicio(projeto.getProximoEvento().getDataInicio());
            dto.setProximoEvento(eventoDto);
        }
        
        return dto;
    }

    public static void updateEntityFromDto(ProjetoRequestDTO dto, Projeto projeto) {
        if (dto.getTitulo() != null) projeto.setTitulo(dto.getTitulo());
        if (dto.getArea() != null) projeto.setArea(dto.getArea());
        if (dto.getUnidadeResponsavel() != null) projeto.setUnidadeResponsavel(dto.getUnidadeResponsavel());
        if (dto.getResumo() != null) projeto.setResumo(dto.getResumo());
        if (dto.getCoordenador() != null) projeto.setCoordenadorId(dto.getCoordenador());
        if (dto.getCronograma() != null) projeto.setCronograma(dto.getCronograma());
        if (dto.getTags() != null) projeto.setTags(dto.getTags());
        if (dto.getStatus() != null) projeto.setStatus(dto.getStatus());
        if (dto.getVagas() != null) projeto.setVagas(dto.getVagas());
        
        if (dto.getProximoEvento() != null) {
            Projeto.ProximoEvento evento = projeto.getProximoEvento();
            if (evento == null) evento = new Projeto.ProximoEvento();
            evento.setTitulo(dto.getProximoEvento().getTitulo());
            evento.setDataInicio(dto.getProximoEvento().getDataInicio());
            projeto.setProximoEvento(evento);
        }
    }
}
