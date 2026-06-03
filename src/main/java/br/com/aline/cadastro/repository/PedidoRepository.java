package br.com.aline.cadastro.repository;

import br.com.aline.cadastro.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p JOIN FETCH p.cliente ORDER BY p.dataPedido DESC")
    List<Pedido> findAllWithCliente();
}
