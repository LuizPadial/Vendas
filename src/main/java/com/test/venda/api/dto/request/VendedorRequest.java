package com.test.venda.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendedorRequest {

    @NotBlank
    private String nomeCompleto;
    @NotBlank
    private String cpf;
}
