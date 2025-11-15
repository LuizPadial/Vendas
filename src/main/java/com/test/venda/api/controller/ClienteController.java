package com.test.venda.api.controller;

import com.test.venda.api.dto.request.ClienteRequest;
import com.test.venda.api.dto.response.ClienteResponse;
import com.test.venda.api.mappers.ClienteMapper;
import com.test.venda.domain.entity.Cliente;
import com.test.venda.domain.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse registrarCliente(@Valid @RequestBody ClienteRequest request) {
        return clienteService.salvar(request);
    }

    @GetMapping
    public List<ClienteResponse> listarClientes(){
        return clienteService.listarClientes();
    }

    @GetMapping("{id}")
    public ClienteResponse buscarPorId(@PathVariable Long id){
        return clienteService.buscarPorId(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterar(@PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {
        clienteService.alterarCliente(id, request);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        clienteService.deletar(id);
    }


}
