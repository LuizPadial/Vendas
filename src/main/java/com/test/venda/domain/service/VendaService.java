package com.test.venda.domain.service;

import com.test.venda.domain.entity.Venda;
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

    private final ProdutoRepository produtoRepository;

    private final VendedorRepository vendedorRepository;


    public List<Venda> listarTodos(){
        List<Venda> vendas = vendaRepository.findAll();
        if(vendas.isEmpty()) {
            throw new NegocioException("Nenhuma venda cadastrado no sistema!");
        }
        return vendas;
    }

    public Optional<Venda> buscarPorId(Long id) {
        Optional<Venda> venda = vendaRepository.findById(id);

        if(venda.isEmpty()){
            throw new NegocioException("Produto não encontrado!");
        }
        return venda;
    }

    public Venda criarVenda(Venda venda) {

        var cliente = clienteRepository.findById(venda.getCliente().getId())
                .orElseThrow(() -> new NegocioException("Cliente não encontrado!"));
        venda.setCliente(cliente);

        var vendedor = vendedorRepository.findById(venda.getVendedor().getId())
                .orElseThrow(() -> new NegocioException("Vendedor não encontrado!"));
        venda.setVendedor(vendedor);

        var produtos = produtoRepository.findAllById(
                venda.getProduto()
                        .stream()
                        .map(Produto::getId)
                        .collect(Collectors.toList())
        );

        if (produtos.isEmpty()) {
            throw new NegocioException("Nenhum produto válido foi informado!");
        }

        venda.setProduto(produtos);

        BigDecimal valorTotal = produtos.stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        venda.setValorTotal(valorTotal);
        venda.setDataVenda(LocalDateTime.now());
        return vendaRepository.save(venda);
    }

    private void defineCliente(Venda venda) {
        var cliente = clienteRepository.findById(venda.getCliente()
                .getId())
                .orElseThrow(() -> new NegocioException("Cliente não encontrado!"));
        venda.setCliente(cliente);
    }

    private void defineVendedor(Venda venda) {
        var vendedor = vendedorRepository.findById(venda.getVendedor()
                .getId())
                .orElseThrow(() -> new NegocioException("Vendedor não encontrado!"));
        venda.setVendedor(vendedor);
    }

    private void defineProdutos(Venda venda) {
        var produtos = produtoRepository.findAllById(venda.getProduto()
                .stream()
                .map(Produto::getId)
                .collect(Collectors.toList()));
        if (produtos.isEmpty()) {
            throw new NegocioException("Produtos não encontrados!");
        }
        venda.setProduto(produtos);
    }



}
