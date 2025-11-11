package com.test.venda.api.controller;

import com.test.venda.api.dto.request.VendedorRequest;
import com.test.venda.api.dto.response.VendedorResponse;
import com.test.venda.domain.entity.Vendedor;
import com.test.venda.domain.service.VendedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/vendedor")
public class VendedorController {

    private final VendedorService service;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendedorResponse setVendedor(@Valid @RequestBody VendedorRequest request) {
        return service.salvar(request);
    }

    @GetMapping
    public List<VendedorResponse> listarTodos(){
        return service.listarVendedores();
    }

    @GetMapping("/{id}")
    public VendedorResponse buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> alterar(@PathVariable Long id,
                                           @Valid @RequestBody VendedorRequest request) {
        return service.alterarVendedor(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
