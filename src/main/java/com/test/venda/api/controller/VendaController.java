package com.test.venda.api.controller;

import com.test.venda.api.dto.request.VendaRequest;
import com.test.venda.api.dto.response.VendaResponse;
import com.test.venda.api.mappers.VendaMapper;
import com.test.venda.domain.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("venda")
public class VendaController {

    private final VendaService vendaService;
    private final VendaMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendaResponse salvar(@Valid @RequestBody VendaRequest request) {
        return vendaService.salvarVenda(request);

    }
    @GetMapping
    public ResponseEntity<List<VendaResponse>> listarVendas() {
        return ResponseEntity.ok(vendaService.listarVendas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.buscarPorId(id));
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        vendaService.deletar(id);
    }
}
