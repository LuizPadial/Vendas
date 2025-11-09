package com.test.venda.domain.service;

import com.test.venda.domain.entity.Cliente;
import com.test.venda.domain.exceptions.NegocioException;
import com.test.venda.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public Cliente salvar(@NotNull Cliente cliente){
        boolean existeCpf = false;
        Optional<Cliente> optCliente = repository.findByCpf(cliente.getCpf());

        if (optCliente.isPresent()) {
            if (!optCliente.get().getId().equals(cliente.getId())) {
                existeCpf = true;
            }
        }
        if (existeCpf) {
            throw new NegocioException("Cpf já cadastrado!");
        }
        return repository.save(cliente);
    }

    public Cliente alterar(Long id, Cliente cliente) {
        Optional<Cliente> optCliente = repository.findById(id);

        if(optCliente.isEmpty()){
            throw new NegocioException("Cliente não cadastrado");
        }
        cliente.setId(id);

        return salvar(cliente);

    }

    public List<Cliente> listarTodos() {
        List<Cliente> clientes = repository.findAll();
        if (clientes.isEmpty()) {
            throw new NegocioException("Nenhum cliente cadastrado no sistema!");
        }
        return clientes;
    }

    public Optional<Cliente> buscarPorId(Long id) {
        Optional<Cliente> cliente = repository.findById(id);

        if (cliente.isEmpty()) {
            throw new NegocioException("Cliente não encontrado!");
        }

        return cliente;
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NegocioException("Cliente não encontrado!");
        }
        repository.deleteById(id);
    }

}
