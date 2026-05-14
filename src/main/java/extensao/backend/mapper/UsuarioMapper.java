package extensao.backend.mapper;

import extensao.backend.entity.Usuario;
import extensao.backend.dto.usuarios.UsuarioRequestDTO;
import extensao.backend.dto.usuarios.UsuarioResponseDTO;
import extensao.backend.dto.usuarios.UsuarioCreateDTO;
import extensao.backend.dto.usuarios.UsuarioUpdateDTO;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequestDTO dto){
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setPapeis(dto.getPapeis());
        usuario.setUnidade(dto.getUnidade());
        usuario.setSemestre(dto.getSemestre());
        usuario.setInteresses(dto.getInteresses());
        usuario.setBio(dto.getBio());
        // se ativo não for informado, por padrão consideramos true (seguindo o mock)
        usuario.setAtivo(dto.getAtivo() == null ? true : dto.getAtivo());
        return usuario;
    }

    public static Usuario toEntity(UsuarioCreateDTO dto){
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setPapeis(dto.getPapeis());
        usuario.setUnidade(dto.getUnidade());
        usuario.setSemestre(dto.getSemestre());
        usuario.setInteresses(dto.getInteresses());
        usuario.setBio(dto.getBio());
        usuario.setAtivo(dto.getAtivo() == null ? true : dto.getAtivo());
        return usuario;
    }

    public static void updateEntityFromDto(UsuarioUpdateDTO dto, Usuario usuario){
        if (dto.getNome() != null && !dto.getNome().isBlank()) usuario.setNome(dto.getNome());
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) usuario.setEmail(dto.getEmail());
        if (dto.getPapeis() != null && !dto.getPapeis().isEmpty()) usuario.setPapeis(dto.getPapeis());
        if (dto.getUnidade() != null && !dto.getUnidade().isBlank()) usuario.setUnidade(dto.getUnidade());
        if (dto.getSemestre() != null && !dto.getSemestre().isBlank()) usuario.setSemestre(dto.getSemestre());
        if (dto.getInteresses() != null && !dto.getInteresses().isEmpty()) usuario.setInteresses(dto.getInteresses());
        if (dto.getBio() != null && !dto.getBio().isBlank()) usuario.setBio(dto.getBio());
        if (dto.getAtivo() != null) usuario.setAtivo(dto.getAtivo());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) usuario.setSenha(dto.getSenha());
    }

    public static UsuarioResponseDTO toResponse(Usuario usuario){
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setPapeis(usuario.getPapeis());
        dto.setUnidade(usuario.getUnidade());
        dto.setSemestre(usuario.getSemestre());
        dto.setInteresses(usuario.getInteresses());
        dto.setBio(usuario.getBio());
        dto.setCriadoEm(usuario.getCriadoEm());
        dto.setAtivo(usuario.isAtivo());
        return dto;
    }

}
