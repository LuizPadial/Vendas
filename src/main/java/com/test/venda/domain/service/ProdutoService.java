package com.test.venda.domain.service;

import com.test.venda.api.dto.request.ClienteRequest;
import com.test.venda.api.dto.request.ProdutoRequest;
import com.test.venda.api.dto.response.ClienteResponse;
import com.test.venda.api.dto.response.ProdutoResponse;
import com.test.venda.api.mappers.ProdutoMapper;
import com.test.venda.domain.entity.Cliente;
import com.test.venda.domain.entity.Produto;
import com.test.venda.domain.entity.Produto;
import com.test.venda.domain.exceptions.NegocioException;
import com.test.venda.domain.repository.ProdutoRepository;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    public ProdutoResponse salvar(ProdutoRequest request) {
        if(repository.findByNomeProduto(request.getNomeProduto()).isPresent()){
            throw new NegocioException("Produto já Cadastrado");
        }
        Produto produto = mapper.toEntity(request);
        return mapper.toModel(repository.save(produto));
    }

    public List<ProdutoResponse> listarProdutos() {
        return mapper.toColletionModel(repository.findAll());
    }

    public ProdutoResponse buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toModel)
                .orElseThrow(() -> new NegocioException("Produto não encontrado"));
    }

    public ProdutoResponse alterarProduto(Long id, ProdutoRequest request) {
        Produto produto = mapper.toEntity(request);
        produto.setId(id);

        if (produto.getId() != null && !repository.existsById(produto.getId())) {
            throw new NegocioException("Produto não encontrado para atualização");
        }
        return mapper.toModel(repository.save(produto));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NegocioException("Produto não encontrado!");
        }
        repository.deleteById(id);
    }


}
