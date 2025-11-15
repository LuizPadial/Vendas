package com.test.venda.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequest {
    @NotBlank
    private String nomeProduto;
    @NotNull
    private BigDecimal preco;
}
