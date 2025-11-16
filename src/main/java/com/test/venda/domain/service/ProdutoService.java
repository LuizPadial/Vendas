package com.test.venda.domain.service;

import com.test.venda.api.common.NomeUtil;
import com.test.venda.api.dto.request.ClienteRequest;
import com.test.venda.api.dto.request.ProdutoRequest;
import com.test.venda.api.dto.request.VendedorRequest;
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
        if (id == null || id <= 0) {
            throw new NegocioException("ID inválido! O ID deve ser maior que zero.");
        }
        return repository.findById(id)
                .map(mapper::toModel)
                .orElseThrow(() -> new NegocioException("Produto não encontrado"));
    }

    public void alterarProduto(Long id, ProdutoRequest request) {
        if (id == null || id <= 0) {
            throw new NegocioException("ID inválido! O ID deve ser maior que zero.");
        }
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new NegocioException("Produto não encontrado."));
        String nomeNormalizado = NomeUtil.normalizarNome(request.getNomeProduto());
        if (NomeUtil.nomeInvalido(nomeNormalizado)) {
            throw new NegocioException("Nome do produto não pode ser vazio!");
        }
        var existente = repository.findByNomeProduto(nomeNormalizado);
        if (existente.isPresent() && !existente.get().getId().equals(id)) {
            throw new NegocioException("Já existe outro produto com esse nome!");
        }
        produto.editar(request);
        produto.setNomeProduto(nomeNormalizado);
        repository.save(produto);
    }

    public void deletar(Long id) {
        if (id == null || id <= 0) {
            throw new NegocioException("ID inválido! O ID deve ser maior que zero.");
        }
        if (!repository.existsById(id)) {
            throw new NegocioException("Produto não encontrado!");
        }
        repository.deleteById(id);
    }
}
