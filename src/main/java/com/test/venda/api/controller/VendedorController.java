package com.test.venda.api.controller;

import com.test.venda.api.dto.request.VendedorRequest;
import com.test.venda.api.dto.response.VendedorResponse;
import com.test.venda.domain.service.VendedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("vendedor")
public class VendedorController {

    private final VendedorService vendedorService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendedorResponse setVendedor(@Valid @RequestBody VendedorRequest request) {
        return vendedorService.salvar(request);
    }

    @GetMapping
    public List<VendedorResponse> listarTodos(){
        return vendedorService.listarVendedores();
    }

    @GetMapping("{id}")
    public VendedorResponse buscarPorId(@PathVariable Long id){
        return vendedorService.buscarPorId(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterar(@PathVariable Long id,
                        @Valid @RequestBody VendedorRequest request) {
        vendedorService.alterarVendedor(id,request);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        vendedorService.deletar(id);
    }
}
