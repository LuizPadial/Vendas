package com.test.venda.api.dto.response;

import com.test.venda.api.dto.request.ProdutoRequest;
import com.test.venda.domain.entity.Produto;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponse {

    private Long id;
    private String nomeProduto;
    private BigDecimal preco;

}
