package com.test.venda.api.dto.request;

import com.test.venda.api.dto.response.ClienteResponse;
import com.test.venda.api.dto.response.ProdutoResponse;
import com.test.venda.api.dto.response.VendedorResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendaRequest {

    private ClienteResponse cliente;
    private VendedorResponse vendedor;
    private List<ProdutoResponse> produto;

}
