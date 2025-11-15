package com.test.venda.api.mappers;

import com.test.venda.api.dto.request.VendaRequest;
import com.test.venda.api.dto.response.VendaResponse;
import com.test.venda.domain.entity.Venda;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendaMapper {

    private final ModelMapper mapper;

    public Venda toEntity(VendaRequest req) {
        Venda venda = new Venda();
        return venda;
    }

    public VendaResponse toModel(Venda venda){
        return mapper.map(venda, VendaResponse.class);
    }
}

