package br.com.aline.cadastro.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoForm {

    @NotNull
    private Long clienteId;

    @NotEmpty
    private List<PedidoItemForm> itens = new ArrayList<>();

    public PedidoForm() {
        itens.add(new PedidoItemForm());
    }
}
