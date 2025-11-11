package com.test.venda.api.dto.response;

import com.test.venda.api.dto.request.ClienteRequest;
import com.test.venda.api.dto.request.ProdutoRequest;
import com.test.venda.domain.entity.Cliente;
import com.test.venda.domain.entity.Produto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    private Long id;
    private String nomeCompleto;
    private String cpf;

    public static Cliente of(ClienteRequest clienteRequest, Long id){
        return Cliente.builder()
                .id(id)
                .nomeCompleto(clienteRequest.getNomeCompleto())
                .cpf(clienteRequest.getCpf()).build();
    }

    public static ClienteResponse of(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nomeCompleto(cliente.getNomeCompleto())
                .cpf(cliente.getCpf()).build();
    }



}
