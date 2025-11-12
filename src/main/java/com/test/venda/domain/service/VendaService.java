package com.test.venda.domain.service;

import com.test.venda.api.dto.request.VendaRequest;
import com.test.venda.api.dto.response.VendaResponse;
import com.test.venda.api.mappers.VendaMapper;
import com.test.venda.domain.entity.Cliente;
import com.test.venda.domain.entity.Venda;
import com.test.venda.domain.entity.Vendedor;
import com.test.venda.domain.exceptions.NegocioException;
import com.test.venda.domain.repository.ClienteRepository;
import com.test.venda.domain.repository.ProdutoRepository;
import com.test.venda.domain.repository.VendaRepository;
import com.test.venda.domain.repository.VendedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.test.venda.domain.entity.Produto;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final VendedorRepository vendedorRepository;
    private final ProdutoRepository produtoRepository;
    private final VendaMapper mapper;

    public VendaResponse salvarVenda(VendaRequest request) {
        var venda = mapper.toEntity(request);
        defineCliente(venda);
        defineVendedor(venda);
        defineProdutos(venda);

        venda.setValorTotal(calcularValorTotal(venda.getProduto()));
        venda.setDataVenda(LocalDateTime.now());

        return mapper.toModel(vendaRepository.save(venda));
    }

    private void defineCliente(Venda venda) {
        var cliente = clienteRepository.findById(venda.getCliente().getId())
                .orElseThrow(() -> new NegocioException("Cliente não encontrado!"));
        venda.setCliente(cliente);
    }

    private void defineVendedor(Venda venda) {
        var vendedor = vendedorRepository.findById(venda.getVendedor().getId())
                .orElseThrow(() -> new NegocioException("Vendedor não encontrado!"));
        venda.setVendedor(vendedor);
    }

    private void defineProdutos(Venda venda) {
        var produtos = produtoRepository.findAllById(
                venda.getProduto()
                        .stream()
                        .map(Produto::getId)
                        .collect(Collectors.toList())
        );

        if (produtos.isEmpty()) {
            throw new NegocioException("Produtos não encontrados!");
        }

        venda.setProduto(produtos);
    }

    private BigDecimal calcularValorTotal(List<Produto> produtos) {
        return produtos.stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
