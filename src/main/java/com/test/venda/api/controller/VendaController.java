package com.test.venda.api.controller;

import com.test.venda.api.dto.request.VendaRequest;
import com.test.venda.api.dto.response.VendaResponse;
import com.test.venda.api.mapper.VendaMapper;
import com.test.venda.domain.entity.Venda;
import com.test.venda.domain.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService service;
    private final VendaMapper mapper;

    @PostMapping
    public ResponseEntity<VendaResponse> salvar(@Valid @RequestBody VendaRequest request) {
        Venda venda = mapper.toVenda(request);
        Venda vendaSalva = service.criarVenda(venda);
        VendaResponse vendaResponse = mapper.toVendaResponse(vendaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaResponse);
    }
}
