package com.test.venda.domain.service;

import com.test.venda.api.common.NomeUtil;
import com.test.venda.api.dto.request.ProdutoRequest;
import com.test.venda.api.dto.response.ProdutoResponse;
import com.test.venda.api.mappers.ProdutoMapper;
import com.test.venda.domain.entity.Produto;
import com.test.venda.domain.exceptions.NegocioException;
import com.test.venda.domain.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveFalharQuandoProdutoJaExiste() {
                ProdutoRequest request = new ProdutoRequest("Caderno", new BigDecimal("12.00"));
        when(produtoRepository.findByNomeProduto("caderno"))
                .thenReturn(Optional.of(new Produto()));
        NegocioException e = assertThrows(NegocioException.class,
                () -> produtoService.salvar(request));
        assertEquals("Produto já cadastrado", e.getMessage());
    }

    @Test
    void deveSalvarProdutoComSucesso() {
        ProdutoRequest request = new ProdutoRequest("Caderno", new BigDecimal("12.00"));
        String nomeNormalizado = "caderno";
        Produto produtoEntity = new Produto();
        produtoEntity.setNomeProduto(nomeNormalizado);
        produtoEntity.setPreco(new BigDecimal("12.00"));
        Produto produtoSalvo = new Produto();
        produtoSalvo.setId(1L);
        produtoSalvo.setNomeProduto(nomeNormalizado);
        produtoSalvo.setPreco(new BigDecimal("12.00"));
        ProdutoResponse produtoResponse = new ProdutoResponse(1L, nomeNormalizado, new BigDecimal("12.00"));
        when(produtoRepository.findByNomeProduto(nomeNormalizado))
                .thenReturn(Optional.empty());
        when(produtoMapper.toEntity(request))
                .thenReturn(produtoEntity);
        when(produtoRepository.save(produtoEntity))
                .thenReturn(produtoSalvo);
        when(produtoMapper.toModel(produtoSalvo))
                .thenReturn(produtoResponse);
        ProdutoResponse resposta = produtoService.salvar(request);
        assertNotNull(resposta);
        assertEquals(1L, resposta.getId());
        assertEquals("caderno", resposta.getNomeProduto());
        assertEquals(new BigDecimal("12.00"), resposta.getPreco());
    }

    @Test
    void deveFalharQuandoNomeNormalizadoInvalido() {
        try (MockedStatic<NomeUtil> util = mockStatic(NomeUtil.class)) {
            ProdutoRequest request = new ProdutoRequest("Caderno", new BigDecimal("10.00"));
            util.when(() -> NomeUtil.normalizarNome("Caderno")).thenReturn("c");
            util.when(() -> NomeUtil.nomeInvalido("c")).thenReturn(true);
            NegocioException e = assertThrows(
                    NegocioException.class,
                    () -> produtoService.salvar(request));
            assertEquals("Nome do produto não pode ser vazio.", e.getMessage());
        }
    }
}
