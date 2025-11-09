package com.test.venda.api.mapper;

import com.test.venda.api.dto.request.VendedorRequest;
import com.test.venda.api.dto.response.VendedorResponse;
import com.test.venda.domain.entity.Vendedor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class VendedorMapper {

    private final ModelMapper mapper;

    public Vendedor toVendedor(VendedorRequest request){
        return mapper.map(request, Vendedor.class);
    }

    public VendedorResponse toVendedorResponse(Vendedor vendedor) {
        return mapper.map(vendedor, VendedorResponse.class);
    }

    public List<VendedorResponse> toVendedorResponseList(List<Vendedor> vendedores) {
        return vendedores.stream()
                .map(this::toVendedorResponse)
                .collect(Collectors.toList());
    }
}
