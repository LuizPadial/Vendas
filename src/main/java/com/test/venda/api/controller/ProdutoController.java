package com.test.venda.api.controller;

import com.test.venda.api.dto.request.ProdutoRequest;
import com.test.venda.api.dto.response.ProdutoResponse;
import com.test.venda.domain.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse registrarProduto(@Valid @RequestBody ProdutoRequest request) {
        return service.salvar(request);
    }

    @GetMapping
    public List<ProdutoResponse> listarProdutos(){
        return service.listarProdutos();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ProdutoResponse buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProdutoResponse alterar(@PathVariable Long id,
                                           @Valid @RequestBody ProdutoRequest request) {
        return service.alterarProduto(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
