package com.test.venda.api.controller;

import com.test.venda.api.dto.request.VendedorRequest;
import com.test.venda.api.dto.response.VendedorResponse;
import com.test.venda.api.mapper.VendedorMapper;
import com.test.venda.domain.entity.Vendedor;
import com.test.venda.domain.service.VendedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vendedor")
public class VendedorController {
    private final VendedorService service;
    private final VendedorMapper mapper;

    @PostMapping
    public ResponseEntity<VendedorResponse> salvar(@Valid @RequestBody VendedorRequest request) {
        Vendedor vendedor = mapper.toVendedor(request);
        Vendedor vendedorSalvo = service.salvar(vendedor);
        VendedorResponse vendedorResponse = mapper.toVendedorResponse(vendedorSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendedorResponse);
    }

    @GetMapping
    public ResponseEntity<List<VendedorResponse>> listarTodos(){
        List<VendedorResponse> vendedorResponse = service.listarTodos()
                .stream()
                .map(mapper::toVendedorResponse)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(vendedorResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorResponse> buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id)
                .map(mapper::toVendedorResponse)
                .map(vendedorResponse -> ResponseEntity.status(HttpStatus.OK).body(vendedorResponse))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendedorResponse> alterar(@PathVariable Long id, @RequestBody VendedorRequest request) {
        Vendedor paciente = mapper.toVendedor(request);
        Vendedor pacienteSalvo = service.alterar(id, paciente);
        VendedorResponse pacienteResponse = mapper.toVendedorResponse(pacienteSalvo);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
