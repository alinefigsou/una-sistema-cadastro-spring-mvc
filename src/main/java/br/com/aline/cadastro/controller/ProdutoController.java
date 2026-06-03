package br.com.aline.cadastro.controller;

import br.com.aline.cadastro.entity.Produto;
import br.com.aline.cadastro.enums.CategoriaProduto;
import br.com.aline.cadastro.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", service.listarTodos());
        model.addAttribute("activePage", "produtos");
        return "produto/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", CategoriaProduto.values());
        model.addAttribute("activePage", "produtos");
        return "produto/form";
    }

    @PostMapping("/novo")
    public String salvarNovo(@Valid @ModelAttribute("produto") Produto produto,
                             BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", CategoriaProduto.values());
            model.addAttribute("activePage", "produtos");
            return "produto/form";
        }
        service.salvar(produto);
        ra.addFlashAttribute("successMsg", "Produto criado com sucesso!");
        return "redirect:/produtos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("produto", service.buscarPorId(id));
        model.addAttribute("categorias", CategoriaProduto.values());
        model.addAttribute("editMode", true);
        model.addAttribute("activePage", "produtos");
        return "produto/form";
    }

    @PostMapping("/{id}/editar")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("produto") Produto produto,
                            BindingResult result, Model model, RedirectAttributes ra) {
        produto.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("categorias", CategoriaProduto.values());
            model.addAttribute("editMode", true);
            model.addAttribute("activePage", "produtos");
            return "produto/form";
        }
        service.salvar(produto);
        ra.addFlashAttribute("successMsg", "Produto atualizado com sucesso!");
        return "redirect:/produtos";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        service.excluir(id);
        ra.addFlashAttribute("successMsg", "Produto excluído.");
        return "redirect:/produtos";
    }
}
