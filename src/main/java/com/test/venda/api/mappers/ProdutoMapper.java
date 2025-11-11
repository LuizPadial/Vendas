package com.test.venda.api.mappers;

import com.test.venda.api.dto.request.ProdutoRequest;
import com.test.venda.api.dto.response.ProdutoResponse;
import com.test.venda.domain.entity.Produto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class ProdutoMapper {

    private final ModelMapper mapper;

    public Produto toEntity(ProdutoRequest request){
        return mapper.map(request, Produto.class);
    }

    public ProdutoResponse toModel(Produto produto) {
        return mapper.map(produto, ProdutoResponse.class);
    }

    public List<ProdutoResponse> toColletionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
    
}
