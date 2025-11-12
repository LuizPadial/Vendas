package com.test.venda.api.dto.response;

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
