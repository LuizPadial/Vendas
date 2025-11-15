package com.test.venda.api.controller;

import com.test.venda.api.dto.request.VendaRequest;
import com.test.venda.api.dto.response.VendaResponse;
import com.test.venda.api.mappers.VendaMapper;
import com.test.venda.domain.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
