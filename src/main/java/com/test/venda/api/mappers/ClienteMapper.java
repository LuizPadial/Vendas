package com.test.venda.api.mappers;

import com.test.venda.api.dto.request.ClienteRequest;
import com.test.venda.api.dto.response.ClienteResponse;
import com.test.venda.domain.entity.Cliente;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class ClienteMapper {

    private final ModelMapper mapper;

    public Cliente toEntity(ClienteRequest request){
        return mapper.map(request, Cliente.class);
    }

    public ClienteResponse toModel(Cliente cliente) {
        return mapper.map(cliente, ClienteResponse.class);
    }

    public List<ClienteResponse> toColletionModel(List<Cliente> clientes) {
        return clientes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
