package com.test.venda.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendedorRequest {

    @NotNull
    private String nomeCompleto;
    @NotNull
    private String cpf;
}
