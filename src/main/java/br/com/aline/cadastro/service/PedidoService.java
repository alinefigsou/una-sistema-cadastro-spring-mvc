package br.com.aline.cadastro.service;

import br.com.aline.cadastro.dto.PedidoForm;
import br.com.aline.cadastro.dto.PedidoItemForm;
import br.com.aline.cadastro.entity.*;
import br.com.aline.cadastro.enums.StatusPedido;
import br.com.aline.cadastro.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAllWithCliente();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));
    }

    @Transactional
    public void salvar(PedidoForm form) {
        Cliente cliente = clienteRepository.findById(form.getClienteId())
            .orElseThrow(() -> new IllegalArgumentException("Cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.PENDENTE);

        BigDecimal total = BigDecimal.ZERO;
        for (PedidoItemForm itemForm : form.getItens()) {
            Produto produto = produtoRepository.findById(itemForm.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto inválido"));

            if (produto.getEstoque() < itemForm.getQuantidade()) {
                throw new IllegalStateException("Estoque insuficiente para: " + produto.getNome());
            }

            PedidoItem item = new PedidoItem();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemForm.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());

            produto.setEstoque(produto.getEstoque() - itemForm.getQuantidade());
            produtoRepository.save(produto);

            total = total.add(produto.getPreco()
                .multiply(BigDecimal.valueOf(itemForm.getQuantidade())));
            pedido.getItens().add(item);
        }

        pedido.setTotal(total);
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void atualizarStatus(Long id, StatusPedido status) {
        Pedido pedido = buscarPorId(id);
        pedido.setStatus(status);
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void excluir(Long id) {
        pedidoRepository.deleteById(id);
    }
}
