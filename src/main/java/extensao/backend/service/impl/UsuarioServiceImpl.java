package extensao.backend.service.impl;

import extensao.backend.service.UsuarioService;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import extensao.backend.entity.Usuario;
import extensao.backend.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public List<Usuario> listAll() {
        return this.usuarioRepository.findAll();
    }

    @Override
    public Usuario create(Usuario usuario) {
        String senhaUsuario = this.passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaUsuario);
        return this.usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(String id, Usuario usuario) {
        Usuario usuarioExistente = this.usuarioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado!"));

        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setPapeis(usuario.getPapeis());
        usuarioExistente.setUnidade(usuario.getUnidade());
        usuarioExistente.setSemestre(usuario.getSemestre());
        usuarioExistente.setInteresses(usuario.getInteresses());
        usuarioExistente.setBio(usuario.getBio());
        usuarioExistente.setAtivo(usuario.isAtivo());
        // Atualiza senha somente se foi fornecida (não vazia)
        if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            usuarioExistente.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
        }

        return this.usuarioRepository.save(usuarioExistente);
    }

    @Override
    public void delete(String id) {
        this.usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        // encoda senha somente se foi fornecida (e não vazia)
        if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
        }
        return this.usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> getById(String id) {
        return this.usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return this.usuarioRepository.findByEmail(email);
    }

}