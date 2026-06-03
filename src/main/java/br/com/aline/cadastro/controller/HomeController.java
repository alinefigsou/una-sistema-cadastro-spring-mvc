package br.com.aline.cadastro.controller;

import br.com.aline.cadastro.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("totalClientes", clienteRepository.count());
        model.addAttribute("totalProdutos", produtoRepository.count());
        model.addAttribute("totalPedidos", pedidoRepository.count());
        model.addAttribute("totalUsuarios", usuarioRepository.count());
        model.addAttribute("activePage", "home");
        return "index";
    }
}
