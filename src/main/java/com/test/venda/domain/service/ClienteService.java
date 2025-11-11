package com.test.venda.domain.service;

import com.test.venda.api.dto.request.ClienteRequest;
import com.test.venda.api.dto.response.ClienteResponse;
import com.test.venda.api.mappers.ClienteMapper;
import com.test.venda.domain.entity.Cliente;
import com.test.venda.domain.exceptions.NegocioException;
import com.test.venda.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public ClienteResponse salvar(ClienteRequest request) {
        Cliente cliente = mapper.toEntity(request);

        return mapper.toModel(repository.save(cliente));
    }

    public List<ClienteResponse> listarClientes() {

        return mapper.toColletionModel(repository.findAll());
    }

    public ClienteResponse buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toModel)
                .orElseThrow(() -> new NegocioException("Cliente não encontrado"));

    }

    public ResponseEntity<Cliente> alterarCliente(Long id, ClienteRequest request) {
        return repository.findById(id).isPresent()
                ? ResponseEntity.ok(repository.save(ClienteResponse.of(request, id)))
                : ResponseEntity.notFound().build();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NegocioException("Cliente não encontrado!");
        }
        repository.deleteById(id);
    }



}
