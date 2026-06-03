package br.com.aline.cadastro.service;

import br.com.aline.cadastro.entity.Cliente;
import br.com.aline.cadastro.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
    }

    @Transactional
    public void salvar(Cliente cliente) {
        repository.save(cliente);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
