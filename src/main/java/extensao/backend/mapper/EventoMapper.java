package extensao.backend.mapper;

import extensao.backend.entity.Evento;
import extensao.backend.dto.eventos.EventoRequestDTO;
import extensao.backend.dto.eventos.EventoResponseDTO;

public class EventoMapper {

    public static Evento toEntity(EventoRequestDTO dto) {
        Evento evento = new Evento();
        evento.setProjetoId(dto.getProjetoId());
        evento.setTitulo(dto.getTitulo());
        evento.setInicioEm(dto.getInicioEm());
        evento.setFimEm(dto.getFimEm());
        evento.setLocal(dto.getLocal());
        evento.setTipo(dto.getTipo());
        return evento;
    }

    public static EventoResponseDTO toResponse(Evento evento) {
        EventoResponseDTO dto = new EventoResponseDTO();
        dto.setId(evento.getId());
        dto.setProjetoId(evento.getProjetoId());
        dto.setTitulo(evento.getTitulo());
        dto.setInicioEm(evento.getInicioEm());
        dto.setFimEm(evento.getFimEm());
        dto.setLocal(evento.getLocal());
        dto.setTipo(evento.getTipo());
        return dto;
    }

    public static void updateEntityFromDto(EventoRequestDTO dto, Evento evento) {
        if (dto.getProjetoId() != null) evento.setProjetoId(dto.getProjetoId());
        if (dto.getTitulo() != null) evento.setTitulo(dto.getTitulo());
        if (dto.getInicioEm() != null) evento.setInicioEm(dto.getInicioEm());
        if (dto.getFimEm() != null) evento.setFimEm(dto.getFimEm());
        if (dto.getLocal() != null) evento.setLocal(dto.getLocal());
        if (dto.getTipo() != null) evento.setTipo(dto.getTipo());
    }
}
