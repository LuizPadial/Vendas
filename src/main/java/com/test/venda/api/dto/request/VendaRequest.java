package com.test.venda.api.dto.request;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendaRequest {

    private ClienteResponse cliente;
    private VendedorResponse vendedor;
    private List<ProdutoResponse> produto;


}
