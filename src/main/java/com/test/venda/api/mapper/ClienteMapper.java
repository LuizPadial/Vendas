package com.test.venda.api.mapper;

import com.test.venda.api.dto.request.ClienteRequest;
import com.test.venda.api.dto.response.ClienteResponse;
import com.test.venda.domain.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClienteMapper {

    private final ModelMapper mapper;

    public Cliente toCliente(ClienteRequest request){
        return mapper.map(request, Cliente.class);
    }

    public ClienteResponse toClienteResponse(Cliente cliente) {
        return mapper.map(cliente, ClienteResponse.class);
    }

    public List<ClienteResponse> toPacienteResponseList(List<Cliente> pacientes) {
        return pacientes.stream()
                .map(this::toClienteResponse)
                .collect(Collectors.toList());
    }


}
