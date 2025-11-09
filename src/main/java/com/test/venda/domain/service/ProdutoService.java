package com.test.venda.domain.service;

import com.test.venda.domain.entity.Cliente;
import com.test.venda.domain.entity.Produto;
import com.test.venda.domain.exceptions.NegocioException;
import com.test.venda.domain.repository.ProdutoRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public Produto salvar(@NotNull Produto produto){

        Optional<Produto> optProduto = repository.findByNome(produto.getNomeProduto());
        if (optProduto.isPresent() && !optProduto.get().getId().equals(produto.getId())) {
            throw new NegocioException("Produto já cadastrado!");
        }
        return repository.save(produto);
    }

    public Produto alterar(Long id, Produto produto) {
        Optional<Produto> optProduto = repository.findById(id);

        if(optProduto.isEmpty()){
            throw new NegocioException("Produto não cadastrado");
        }
        produto.setId(id);

        return salvar(produto);
    }

    public List<Produto> listarTodos(){
        List<Produto> produtos = repository.findAll();
        if(produtos.isEmpty()) {
            throw new NegocioException("Nenhum produto cadastrado no sistema!");
        }
        return produtos;
    }

    public Optional<Produto> buscarPorId(Long id) {
        Optional<Produto> produto = repository.findById(id);

        if(produto.isEmpty()){
            throw new NegocioException("Produto não encontrado!");
        }
        return produto;
    }
    public void deletar(Long id){
        if(!repository.existsById(id)){
            throw new NegocioException("Cliente não encontrado");
        }
        repository.deleteById(id);
    }


}
