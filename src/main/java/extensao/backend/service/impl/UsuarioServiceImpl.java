package extensao.backend.service.impl;

import extensao.backend.service.UsuarioService;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.security.SecureRandom;
import java.util.Base64;
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
        if (usuario.getPapeis() == null || usuario.getPapeis().isEmpty()) {
            usuario.setPapeis(java.util.List.of("Estudante"));
        }
        usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
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
        return this.usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updatePapeis(String id, java.util.List<String> papeis) {
        Usuario usuario = this.usuarioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));
        usuario.setPapeis(papeis);
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

    @Override
    public Usuario resolverOuCriarCoordenadorPlaceholder(String nome, String email, String unidade) {
        return usuarioRepository.findByEmail(email).orElseGet(() -> {
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setUnidade(unidade == null ? "" : unidade);
            usuario.setPapeis(List.of("Professor"));
            usuario.setAtivo(true);
            usuario.setEmailVerificado(false);
            usuario.setContaImportada(true);
            // senha aleatoria e inutilizavel: ninguem loga com ela, so serve para
            // satisfazer a validacao do campo ate a pessoa reivindicar a conta
            usuario.setSenha(passwordEncoder.encode(gerarSenhaAleatoriaInutilizavel()));
            return usuarioRepository.save(usuario);
        });
    }

    private String gerarSenhaAleatoriaInutilizavel() {
        byte[] bytes = new byte[24];
        new SecureRandom().nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

}