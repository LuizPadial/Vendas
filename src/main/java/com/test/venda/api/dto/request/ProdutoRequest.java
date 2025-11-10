package com.test.venda.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequest {

    @NotNull
    private String nomeProduto;
    @NotNull
    private String preco;
}
