package com.test.venda.api.mappers;

import com.test.venda.api.dto.request.VendaRequest;
import com.test.venda.api.dto.response.VendaResponse;
import com.test.venda.domain.entity.Venda;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VendaMapper {

    private final ModelMapper mapper;

    public Venda toVenda(VendaRequest request){
        return mapper.map(request, Venda.class);
    }
    public VendaResponse toVendaResponse(Venda venda) {
        return mapper.map(venda, VendaResponse.class);
    }

    public List<VendaResponse> toVendaResponseList(List<Venda> vendas) {
        return vendas.stream()
                .map(this::toVendaResponse)
                .collect(Collectors.toList());
    }


}
