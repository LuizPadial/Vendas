package com.test.venda.api.dto.response;

import com.test.venda.api.dto.request.VendedorRequest;
import com.test.venda.domain.entity.Vendedor;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendedorResponse {

    private Long id;
    private String nomeCompleto;
    private String cpf;

}
