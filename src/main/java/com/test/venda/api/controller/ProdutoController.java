package com.test.venda.api.controller;

import com.test.venda.api.dto.request.ProdutoRequest;
import com.test.venda.api.dto.response.ProdutoResponse;
import com.test.venda.api.mapper.ProdutoMapper;
import com.test.venda.domain.entity.Produto;
import com.test.venda.domain.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService service;
    private final ProdutoMapper mapper;

    @PostMapping
    public ResponseEntity<ProdutoResponse> salvar(@Valid @RequestBody ProdutoRequest request){
        Produto produto = mapper.toProduto(request);
        Produto produtoSalvo = service.salvar(produto);
        ProdutoResponse produtoResponse = mapper.toProdutoResponse(produtoSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponse);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listarTodos(){
        List<ProdutoResponse> produtoResponse = service.listarTodos()
                .stream()
                .map(mapper::toProdutoResponse)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(produtoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id)
                .map(mapper::toProdutoResponse)
                .map(produtoResponse -> ResponseEntity.status(HttpStatus.OK).body(produtoResponse))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> alterar(@PathVariable Long id, @RequestBody ProdutoRequest request) {
        Produto produto= mapper.toProduto(request);
        Produto produtoSalvo = service.alterar(id, produto);
        ProdutoResponse produtoResponse = mapper.toProdutoResponse(produtoSalvo);
        return ResponseEntity.status(HttpStatus.OK).body(produtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
