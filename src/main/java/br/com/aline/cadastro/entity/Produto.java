package br.com.aline.cadastro.entity;

import br.com.aline.cadastro.enums.CategoriaProduto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @NotNull
    @DecimalMin("0.01")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @Min(0)
    @Column(nullable = false)
    private int estoque;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CategoriaProduto categoria;

    @Column(nullable = false)
    private boolean ativo = true;
}
