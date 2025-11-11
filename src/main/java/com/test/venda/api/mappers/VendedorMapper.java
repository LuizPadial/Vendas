package com.test.venda.api.mappers;

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

    public Vendedor toEntity(VendedorRequest request){
        return mapper.map(request, Vendedor.class);
    }

    public VendedorResponse toModel(Vendedor vendedor) {
        return mapper.map(vendedor, VendedorResponse.class);
    }

    public List<VendedorResponse> toColletionModel(List<Vendedor> vendedores) {
        return vendedores.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
