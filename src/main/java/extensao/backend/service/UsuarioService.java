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
    Usuario updatePapeis(String id, List<String> papeis);
    void delete(String id);
    Optional<Usuario> findByEmail(String email);

    /**
     * Busca um Professor pelo e-mail; se nao existir, cria uma conta "placeholder"
     * (sem senha utilizavel, marcada como contaImportada) so para ser o coordenador
     * de projetos importados. A pessoa pode assumir essa conta depois se cadastrando
     * normalmente com o mesmo e-mail.
     */
    Usuario resolverOuCriarCoordenadorPlaceholder(String nome, String email, String unidade);
}
