package br.com.aline.cadastro.controller;

import br.com.aline.cadastro.dto.PedidoForm;
import br.com.aline.cadastro.enums.StatusPedido;
import br.com.aline.cadastro.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        model.addAttribute("activePage", "pedidos");
        return "pedido/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("pedidoForm", new PedidoForm());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("produtos", produtoService.listarAtivos());
        model.addAttribute("activePage", "pedidos");
        return "pedido/form";
    }

    @PostMapping("/novo")
    public String salvar(@Valid @ModelAttribute("pedidoForm") PedidoForm form,
                         BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("produtos", produtoService.listarAtivos());
            model.addAttribute("activePage", "pedidos");
            return "pedido/form";
        }
        try {
            pedidoService.salvar(form);
            ra.addFlashAttribute("successMsg", "Pedido criado com sucesso!");
        } catch (IllegalStateException e) {
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("produtos", produtoService.listarAtivos());
            model.addAttribute("activePage", "pedidos");
            return "pedido/form";
        }
        return "redirect:/pedidos";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("pedido", pedidoService.buscarPorId(id));
        model.addAttribute("statuses", StatusPedido.values());
        model.addAttribute("activePage", "pedidos");
        return "pedido/detail";
    }

    @PostMapping("/{id}/status")
    public String atualizarStatus(@PathVariable Long id,
                                  @RequestParam StatusPedido status,
                                  RedirectAttributes ra) {
        pedidoService.atualizarStatus(id, status);
        ra.addFlashAttribute("successMsg", "Status atualizado com sucesso!");
        return "redirect:/pedidos/" + id;
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        pedidoService.excluir(id);
        ra.addFlashAttribute("successMsg", "Pedido excluído.");
        return "redirect:/pedidos";
    }
}
