package br.com.aline.cadastro.controller;

import br.com.aline.cadastro.entity.Usuario;
import br.com.aline.cadastro.enums.Role;
import br.com.aline.cadastro.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", service.listarTodos());
        model.addAttribute("activePage", "usuarios");
        return "usuario/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", Role.values());
        model.addAttribute("activePage", "usuarios");
        return "usuario/form";
    }

    @PostMapping("/novo")
    public String salvarNovo(@Valid @ModelAttribute("usuario") Usuario usuario,
                             BindingResult result,
                             @RequestParam(value = "rawSenha", defaultValue = "") String rawSenha,
                             Model model,
                             RedirectAttributes ra) {
        if (rawSenha.isBlank()) {
            result.rejectValue("senha", "required", "Senha é obrigatória para novo usuário");
        }
        if (result.hasErrors()) {
            model.addAttribute("roles", Role.values());
            model.addAttribute("activePage", "usuarios");
            return "usuario/form";
        }
        service.salvar(usuario, rawSenha);
        ra.addFlashAttribute("successMsg", "Usuário criado com sucesso!");
        return "redirect:/usuarios";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", service.buscarPorId(id));
        model.addAttribute("roles", Role.values());
        model.addAttribute("editMode", true);
        model.addAttribute("activePage", "usuarios");
        return "usuario/form";
    }

    @PostMapping("/{id}/editar")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("usuario") Usuario usuario,
                            BindingResult result,
                            @RequestParam(value = "rawSenha", defaultValue = "") String rawSenha,
                            Model model,
                            RedirectAttributes ra) {
        usuario.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("roles", Role.values());
            model.addAttribute("editMode", true);
            model.addAttribute("activePage", "usuarios");
            return "usuario/form";
        }
        if (rawSenha.isBlank()) {
            Usuario existing = service.buscarPorId(id);
            usuario.setSenha(existing.getSenha());
            service.salvar(usuario, null);
        } else {
            service.salvar(usuario, rawSenha);
        }
        ra.addFlashAttribute("successMsg", "Usuário atualizado com sucesso!");
        return "redirect:/usuarios";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        service.excluir(id);
        ra.addFlashAttribute("successMsg", "Usuário excluído.");
        return "redirect:/usuarios";
    }
}
