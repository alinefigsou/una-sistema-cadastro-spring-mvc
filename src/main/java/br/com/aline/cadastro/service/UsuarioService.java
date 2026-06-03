package br.com.aline.cadastro.service;

import br.com.aline.cadastro.entity.Usuario;
import br.com.aline.cadastro.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));
    }

    @Transactional
    public void salvar(Usuario usuario, String rawSenha) {
        if (rawSenha != null && !rawSenha.isBlank()) {
            usuario.setSenha(passwordEncoder.encode(rawSenha));
        }
        repository.save(usuario);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
