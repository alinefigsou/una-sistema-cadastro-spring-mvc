package br.com.aline.cadastro.service;

import br.com.aline.cadastro.entity.Produto;
import br.com.aline.cadastro.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public List<Produto> listarAtivos() {
        return repository.findByAtivoTrue();
    }

    public Produto buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
    }

    @Transactional
    public void salvar(Produto produto) {
        repository.save(produto);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
