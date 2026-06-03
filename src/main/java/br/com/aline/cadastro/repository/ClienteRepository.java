package br.com.aline.cadastro.repository;

import br.com.aline.cadastro.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByAtivoTrue();
    boolean existsByCpfCnpj(String cpfCnpj);
}
