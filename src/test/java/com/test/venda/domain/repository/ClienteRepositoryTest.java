package com.test.venda.domain.repository;

import com.test.venda.domain.entity.Cliente;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class ClienteRepositoryTest {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    EntityManager entityManager;
    @Test
    void findByCpfSucesso() {
        Cliente cliente = salvarCliente();
        Cliente encontrado = clienteRepository.findByCpf(cliente.getCpf()).orElse(null);
        assertNotNull(encontrado);
        assertEquals(cliente.getCpf(), encontrado.getCpf());
    }

    private Cliente salvarCliente() {
        Cliente novoCliente = new Cliente();
        novoCliente.setNomeCompleto("Cristiano Ronaldo");
        novoCliente.setCpf("11122233344");
        entityManager.persist(novoCliente);
        return novoCliente;
    }


}