package com.test.venda.domain.service;

import com.test.venda.domain.entity.Vendedor;
import com.test.venda.domain.exceptions.NegocioException;
import com.test.venda.domain.repository.VendedorRepository;
import com.test.venda.domain.repository.VendedorRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
@RequiredArgsConstructor
public class VendedorService {
    private final VendedorRepository repository;

    public Vendedor salvar(@NotNull Vendedor vendedor){
        boolean existeCpf = false;
        Optional<Vendedor> optVendedor = repository.findByCpf(vendedor.getCpf());

        if (optVendedor.isPresent()) {
            if (!optVendedor.get().getId().equals(vendedor.getId())) {
                existeCpf = true;
            }
        }
        if (existeCpf) {
            throw new NegocioException("Cpf já cadastrado!");
        }
        return repository.save(vendedor);
    }

    public Vendedor alterar(Long id, Vendedor vendedor) {
        Optional<Vendedor> optVendedor = repository.findById(id);

        if(optVendedor.isEmpty()){
            throw new NegocioException("Vendedor não cadastrado");
        }
        vendedor.setId(id);

        return salvar(vendedor);

    }

    public List<Vendedor> listarTodos() {
        List<Vendedor> vendedors = repository.findAll();
        if (vendedors.isEmpty()) {
            throw new NegocioException("Nenhum vendedor cadastrado no sistema!");
        }
        return vendedors;
    }

    public Optional<Vendedor> buscarPorId(Long id) {
        Optional<Vendedor> vendedor = repository.findById(id);

        if (vendedor.isEmpty()) {
            throw new NegocioException("Vendedor não encontrado!");
        }

        return vendedor;
    }

    public void deletar(Long id) {
        if (id == null) {
            throw new NegocioException("Você precisa passar um id válido.");
        }
        if (!repository.existsById(id)) {
            throw new NegocioException("Vendedor não encontrado!");
        }
        repository.deleteById(id);
    }
}
