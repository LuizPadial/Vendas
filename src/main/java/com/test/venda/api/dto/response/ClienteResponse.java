package com.test.venda.api.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    private Long id;
    private String nomeCompleto;
    private String cpf;

}
