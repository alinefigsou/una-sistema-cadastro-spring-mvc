package br.com.aline.cadastro.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoItemForm {

    @NotNull
    private Long produtoId;

    @Min(1)
    private int quantidade = 1;
}
