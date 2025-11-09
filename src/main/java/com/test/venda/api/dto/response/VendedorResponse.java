package com.test.venda.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendedorResponse {

    private Long id;
    private String nomeCompleto;
    private String cpf;

}
