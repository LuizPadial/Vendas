package com.test.venda.api.controller;

import com.test.venda.api.dto.request.ClienteRequest;
import com.test.venda.api.dto.response.ClienteResponse;
import com.test.venda.api.mapper.ClienteMapper;
import com.test.venda.domain.entity.Cliente;
import com.test.venda.domain.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;
    private final ClienteMapper mapper;

    @PostMapping
    public ResponseEntity<ClienteResponse> salvar(@Valid @RequestBody ClienteRequest request) {
        Cliente cliente = mapper.toCliente(request);
        Cliente clienteSalvo = service.salvar(cliente);
        ClienteResponse clienteResponse = mapper.toClienteResponse(clienteSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listarTodos(){
        List<ClienteResponse> clienteResponse = service.listarTodos()
                .stream()
                .map(mapper::toClienteResponse)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(clienteResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id)
                .map(mapper::toClienteResponse)
                .map(clienteResponse -> ResponseEntity.status(HttpStatus.OK).body(clienteResponse))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> alterar(@PathVariable Long id, @RequestBody ClienteRequest request) {
        Cliente cliente = mapper.toCliente(request);
        Cliente clienteSalvo = service.alterar(id, cliente);
        ClienteResponse clienteResponse = mapper.toClienteResponse(clienteSalvo);
        return ResponseEntity.status(HttpStatus.OK).body(clienteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
