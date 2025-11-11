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

    public static Vendedor of(VendedorRequest vendedorRequest, Long id){
        return Vendedor.builder()
                .id(id)
                .nomeCompleto(vendedorRequest.getNomeCompleto())
                .cpf(vendedorRequest.getCpf()).build();
    }

    public static VendedorResponse of(Vendedor vendedor) {
        return VendedorResponse.builder()
                .id(vendedor.getId())
                .nomeCompleto(vendedor.getNomeCompleto())
                .cpf(vendedor.getCpf()).build();
    }
}
