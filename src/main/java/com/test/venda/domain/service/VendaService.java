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

    public VendaResponse criarVenda(VendaRequest request) {
        Venda venda = mapper.toEntity(request);

        venda.setCliente(buscarCliente(venda.getCliente().getId()));
        venda.setVendedor(buscarVendedor(venda.getVendedor().getId()));
        venda.setProduto(buscarProdutos(venda));
        venda.setValorTotal(calcularValorTotal(venda.getProduto()));
        venda.setDataVenda(LocalDateTime.now());

        Venda salvo = vendaRepository.save(venda);
        return mapper.toModel(salvo);
    }

    private Cliente buscarCliente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Cliente não encontrado!"));
    }

    private Vendedor buscarVendedor(Long id) {
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Vendedor não encontrado!"));
    }

    private List<Produto> buscarProdutos(Venda venda) {
        return venda.getProduto().stream()
                .map(p -> produtoRepository.findById(p.getId())
                        .orElseThrow(() -> new NegocioException("Produto não encontrado!")))
                .toList();
    }

    private BigDecimal calcularValorTotal(List<Produto> produtos) {
        return produtos.stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
