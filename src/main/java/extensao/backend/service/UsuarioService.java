package extensao.backend.service;

import java.util.List;
import java.util.Optional;

import extensao.backend.entity.Usuario;

public interface UsuarioService {
    Usuario create(Usuario usuario);
    Optional<Usuario> getById(String id);
    List<Usuario> listAll();
    Usuario update(String id, Usuario usuario);
    Usuario save(Usuario usuario);
    void delete(String id);
    Optional<Usuario> findByEmail(String email);
}
