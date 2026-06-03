package br.com.aline.cadastro.controller;

import br.com.aline.cadastro.entity.Cliente;
import br.com.aline.cadastro.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", service.listarTodos());
        model.addAttribute("activePage", "clientes");
        return "cliente/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("activePage", "clientes");
        return "cliente/form";
    }

    @PostMapping("/novo")
    public String salvarNovo(@Valid @ModelAttribute("cliente") Cliente cliente,
                             BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("activePage", "clientes");
            return "cliente/form";
        }
        service.salvar(cliente);
        ra.addFlashAttribute("successMsg", "Cliente criado com sucesso!");
        return "redirect:/clientes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", service.buscarPorId(id));
        model.addAttribute("editMode", true);
        model.addAttribute("activePage", "clientes");
        return "cliente/form";
    }

    @PostMapping("/{id}/editar")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("cliente") Cliente cliente,
                            BindingResult result, Model model, RedirectAttributes ra) {
        cliente.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("editMode", true);
            model.addAttribute("activePage", "clientes");
            return "cliente/form";
        }
        service.salvar(cliente);
        ra.addFlashAttribute("successMsg", "Cliente atualizado com sucesso!");
        return "redirect:/clientes";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        service.excluir(id);
        ra.addFlashAttribute("successMsg", "Cliente excluído.");
        return "redirect:/clientes";
    }
}
