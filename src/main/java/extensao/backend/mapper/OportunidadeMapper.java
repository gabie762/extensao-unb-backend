package extensao.backend.mapper;

import extensao.backend.entity.Oportunidade;
import extensao.backend.dto.oportunidades.OportunidadeRequestDTO;
import extensao.backend.dto.oportunidades.OportunidadeResponseDTO;

public class OportunidadeMapper {

    public static Oportunidade toEntity(OportunidadeRequestDTO dto) {
        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setProjetoId(dto.getProjetoId());
        oportunidade.setTitulo(dto.getTitulo());
        oportunidade.setDescricao(dto.getDescricao());
        oportunidade.setSobreProjeto(dto.getSobreProjeto());
        oportunidade.setObjetivos(dto.getObjetivos());
        oportunidade.setAtividadesDesenvolvidas(dto.getAtividadesDesenvolvidas());
        oportunidade.setComoParticipar(dto.getComoParticipar());
        oportunidade.setCertificado(dto.isCertificado());
        oportunidade.setRequisitos(dto.getRequisitos());
        oportunidade.setQtdeVagas(dto.getQtdeVagas());
        oportunidade.setPrazoInscricao(dto.getPrazoInscricao());
        oportunidade.setTipo(dto.getTipo());
        oportunidade.setLocal(dto.getLocal());
        oportunidade.setCargaHoraria(dto.getCargaHoraria());
        oportunidade.setStatus(dto.getStatus());
        return oportunidade;
    }

    public static OportunidadeResponseDTO toResponse(Oportunidade oportunidade) {
        OportunidadeResponseDTO dto = new OportunidadeResponseDTO();
        dto.setId(oportunidade.getId());
        dto.setProjetoId(oportunidade.getProjetoId());
        dto.setTitulo(oportunidade.getTitulo());
        dto.setDescricao(oportunidade.getDescricao());
        dto.setSobreProjeto(oportunidade.getSobreProjeto());
        dto.setObjetivos(oportunidade.getObjetivos());
        dto.setAtividadesDesenvolvidas(oportunidade.getAtividadesDesenvolvidas());
        dto.setComoParticipar(oportunidade.getComoParticipar());
        dto.setCertificado(oportunidade.isCertificado());
        dto.setRequisitos(oportunidade.getRequisitos());
        dto.setQtdeVagas(oportunidade.getQtdeVagas());
        dto.setPrazoInscricao(oportunidade.getPrazoInscricao());
        dto.setTipo(oportunidade.getTipo());
        dto.setLocal(oportunidade.getLocal());
        dto.setCargaHoraria(oportunidade.getCargaHoraria());
        dto.setStatus(oportunidade.getStatus());
        dto.setCriadoEm(oportunidade.getCriadoEm());
        dto.setAtualizadoEm(oportunidade.getAtualizadoEm());
        return dto;
    }

    public static void updateEntityFromDto(OportunidadeRequestDTO dto, Oportunidade oportunidade) {
        if (dto.getProjetoId() != null) oportunidade.setProjetoId(dto.getProjetoId());
        if (dto.getTitulo() != null) oportunidade.setTitulo(dto.getTitulo());
        if (dto.getDescricao() != null) oportunidade.setDescricao(dto.getDescricao());
        if (dto.getSobreProjeto() != null) oportunidade.setSobreProjeto(dto.getSobreProjeto());
        if (dto.getObjetivos() != null) oportunidade.setObjetivos(dto.getObjetivos());
        if (dto.getAtividadesDesenvolvidas() != null) oportunidade.setAtividadesDesenvolvidas(dto.getAtividadesDesenvolvidas());
        if (dto.getComoParticipar() != null) oportunidade.setComoParticipar(dto.getComoParticipar());
        oportunidade.setCertificado(dto.isCertificado());
        if (dto.getRequisitos() != null) oportunidade.setRequisitos(dto.getRequisitos());
        if (dto.getPrazoInscricao() != null) oportunidade.setPrazoInscricao(dto.getPrazoInscricao());
        if (dto.getTipo() != null) oportunidade.setTipo(dto.getTipo());
        if (dto.getLocal() != null) oportunidade.setLocal(dto.getLocal());
        if (dto.getCargaHoraria() != null) oportunidade.setCargaHoraria(dto.getCargaHoraria());
        if (dto.getStatus() != null) oportunidade.setStatus(dto.getStatus());
    }
}
