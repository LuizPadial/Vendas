package com.test.venda.domain.service;
import com.test.venda.api.dto.request.VendedorRequest;
import com.test.venda.api.dto.response.VendedorResponse;
import com.test.venda.api.mappers.VendedorMapper;
import com.test.venda.domain.entity.Vendedor;
import com.test.venda.domain.exceptions.NegocioException;
import com.test.venda.domain.repository.VendedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class VendedorService {

    private final VendedorRepository repository;
    private final VendedorMapper mapper;

    public VendedorResponse salvar(VendedorRequest request) {
        Vendedor vendedor = mapper.toEntity(request);

        return mapper.toModel(repository.save(vendedor));
    }

    public List<VendedorResponse> listarVendedores() {

        return mapper.toColletionModel(repository.findAll());
    }

    public VendedorResponse buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toModel)
                .orElseThrow(() -> new NegocioException("Vendedor não encontrado"));

    }

    public ResponseEntity<Vendedor> alterarVendedor(Long id, VendedorRequest request) {
        return repository.findById(id).isPresent()
                ? ResponseEntity.ok(repository.save(VendedorResponse.of(request, id)))
                : ResponseEntity.notFound().build();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NegocioException("Vendedor não encontrado!");
        }
        repository.deleteById(id);
    }

}
