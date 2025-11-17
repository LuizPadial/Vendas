package com.test.venda.domain.service;

import com.test.venda.api.dto.request.VendedorRequest;
import com.test.venda.api.dto.response.VendedorResponse;
import com.test.venda.api.mappers.VendedorMapper;
import com.test.venda.domain.entity.Vendedor;
import com.test.venda.domain.exceptions.NegocioException;
import com.test.venda.domain.repository.VendedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendedorServiceTest {

    @Mock
    private VendedorRepository vendedorRepository;

    @Mock
    private VendedorMapper vendedorMapper;

    @InjectMocks
    private VendedorService vendedorService;

    @Test
    void deveFalharQuandoCpfInvalido() {
        VendedorRequest vendedorRequest = new VendedorRequest("Luiz Fernando", " ");
        assertThrows(NegocioException.class, ()-> vendedorService.salvar(vendedorRequest));
    }

    @Test
    void deveFalharQuandoNomeVazio() {
        VendedorRequest vendedorRequest = new VendedorRequest("", "12345678901");
        assertThrows(NegocioException.class, () -> vendedorService.salvar(vendedorRequest));
    }

    @Test
    void deveFalharQuandoCpfContemLetras() {
        VendedorRequest vendedorRequest = new VendedorRequest("Luiz", "11A222B3311");
        assertThrows(NegocioException.class, () -> vendedorService.salvar(vendedorRequest));
    }

    @Test
    void deveFalharQuandoCpfMenorQue11() {
        VendedorRequest vendedorRequest = new VendedorRequest("Luiz", "1234567890");
        assertThrows(NegocioException.class, () -> vendedorService.salvar(vendedorRequest));
    }

    @Test
    void deveFalharQuandoCpfMaiorQue11() {
        VendedorRequest vendedorRequest = new VendedorRequest("Luiz", "123456789012345");
        assertThrows(NegocioException.class, () -> vendedorService.salvar(vendedorRequest));
    }

    @Test
    void deveFalharQuandoCpfjaExiste(){
        VendedorRequest vendedorRequest = new VendedorRequest("Luiz Fernando","11122233311");
        when(vendedorRepository.findByCpf("11122233311"))
                .thenReturn(Optional.of(new Vendedor()));
        NegocioException e = assertThrows(NegocioException.class,
                ()-> vendedorService.salvar(vendedorRequest));
        assertEquals("CPF já cadastrado", e.getMessage());
    }

    @Test
    void deveSalvarVendedorComSucesso(){
        VendedorRequest vendedorRequest = new VendedorRequest("Luiz Fernando","11122233311");
        Vendedor entity = new Vendedor();
        VendedorResponse vendedorResponse = new VendedorResponse();
        when(vendedorRepository.findByCpf("11122233311"))
                .thenReturn(Optional.empty());
        when(vendedorMapper.toEntity(vendedorRequest))
                .thenReturn(entity);
        when(vendedorRepository.save(entity))
                .thenReturn(entity);
        when(vendedorMapper.toModel(entity))
                .thenReturn(vendedorResponse);
        VendedorResponse devolve = vendedorService.salvar(vendedorRequest);
        verify(vendedorRepository).save(entity);
    }

    @Test
    void deveNormalizarCpfAntesDeSalvar(){
        VendedorRequest vendedorRequest = new VendedorRequest("Luiz Fernando","111.222.333.11");
        Vendedor entity = new Vendedor();
        VendedorResponse vendedorResponse = new VendedorResponse();
        when(vendedorRepository.findByCpf("11122233311"))
                .thenReturn(Optional.empty());
        when(vendedorMapper.toEntity(vendedorRequest))
                .thenReturn((entity));
        when(vendedorRepository.save(entity))
                .thenReturn(entity);
        vendedorService.salvar(vendedorRequest);
        assertEquals("11122233311",entity.getCpf());
    }

    @Test
    void deveChamarMapperParaConversao(){
        VendedorRequest vendedorRequest = new VendedorRequest("Luiz Fernando","11122233311");
        Vendedor entity = new Vendedor();
        VendedorResponse vendedorResponse = new VendedorResponse();
        when(vendedorRepository.findByCpf("11122233311"))
                .thenReturn(Optional.empty());
        when(vendedorMapper.toEntity(vendedorRequest))
                .thenReturn((entity));
        when(vendedorRepository.save(entity))
                .thenReturn(entity);
        vendedorService.salvar(vendedorRequest);
        assertEquals("11122233311",entity.getCpf());
        verify(vendedorMapper).toEntity(vendedorRequest);
        verify(vendedorMapper).toModel(entity);
    }

    @Test
    void deveRetornarListaDeVendedores(){
        var vendedor = new Vendedor();
        List<Vendedor> vendedors = List.of(vendedor);
        var response = new VendedorResponse();
        List<VendedorResponse> responses = List.of(response);
        when(vendedorRepository.findAll())
                .thenReturn(vendedors);
        when(vendedorMapper.toColletionModel(vendedors))
                .thenReturn(responses);
        List<VendedorResponse> resultado = vendedorService.listarVendedores();
        assertEquals(1,resultado.size());
        verify(vendedorRepository).findAll();
        verify(vendedorMapper).toColletionModel(vendedors);
    }

    @Test
    void deveRetornarListaVazia(){
        when(vendedorRepository.findAll()).thenReturn(List.of());
        when(vendedorMapper.toColletionModel(List.of())).thenReturn(List.of());
        List<VendedorResponse> resultado =vendedorService.listarVendedores();
        assertTrue(resultado.isEmpty());
    }

    @Test
    void develancarErroSeRepositoryFalhar() {
        when(vendedorRepository.findAll())
                .thenThrow(new NegocioException("Erro nobanco"));
        assertThrows(NegocioException.class,()-> vendedorService.listarVendedores());
    }

    @Test
    void deveLancarErroSeMapperFalhar(){
        when(vendedorRepository.findAll())
                .thenReturn(List.of(new Vendedor()));
        when(vendedorMapper.toColletionModel(Mockito.anyList()))
                .thenThrow(new NegocioException("Erro no Mapper"));
        assertThrows(RuntimeException.class, () -> vendedorService.listarVendedores());
    }

    @Test
    void deveRetornarVendedorQuandoIdExistir(){
        Long id = 1L;
        Vendedor vendedor = new Vendedor();
        VendedorResponse vendedorResponse = new VendedorResponse();
        when(vendedorRepository.findById(id)).thenReturn(Optional.of(vendedor));
        when(vendedorMapper.toModel(vendedor)).thenReturn(vendedorResponse);
        VendedorResponse resultado = vendedorService.buscarPorId(id);
        assertNotNull(resultado);
        assertEquals(vendedorResponse, resultado);
        verify(vendedorRepository).findById(id);
        verify(vendedorMapper).toModel(vendedor);
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoExistir() {
        Long id = 1L;
        when(vendedorRepository.findById(id)).thenReturn(Optional.empty());
        NegocioException exception = assertThrows(NegocioException.class,
                () -> vendedorService.buscarPorId(id));
        assertEquals("Vendedor não encontrado", exception.getMessage());
        verify(vendedorRepository).findById(id);
        verify(vendedorMapper, never()).toModel(any());
    }

    @Test
    void deveLancarExcecaoQuandoIdForNulo() {
        assertThrows(NegocioException.class, () -> vendedorService.buscarPorId(null));
    }

    @Test
    void naoDeveChamarMapperQuandoVendedorNaoExistir() {
        Long id = 10L;
        when(vendedorRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NegocioException.class, () -> vendedorService.buscarPorId(id));
        verify(vendedorMapper, never()).toModel(any());
    }

    @Test
    void naoDeveValidarDuplicidadeQuandoCpfForIgual() {
        Long id = 1L;
        Vendedor vendedorExistente = new Vendedor();
        vendedorExistente.setCpf("11122233311");
        VendedorRequest vendedorRequest = new VendedorRequest("Luiz Fernando", "11122233311");
        when(vendedorRepository.findById(id))
                .thenReturn(Optional.of(vendedorExistente));
        vendedorService.alterarVendedor(id, vendedorRequest);
        verify(vendedorRepository, never()).findByCpf(any());
        verify(vendedorRepository).save(vendedorExistente);
    }

    @Test
    void deveLancarExcecaoQuandoCpfSerDiferenteAtualizarParaCpfRepetido() {
        Long id = 1L;
        Vendedor vendedorExistente = new Vendedor();
        vendedorExistente.setCpf("11111111111");
        VendedorRequest vendedorRequest = new VendedorRequest("Luiz Fernando", "33311122211");
        when(vendedorRepository.findById(id)).thenReturn(Optional.of(vendedorExistente));
        when(vendedorRepository.findByCpf("33311122211"))
                .thenReturn(Optional.of(new Vendedor()));
        NegocioException ex = assertThrows(
                NegocioException.class,
                () -> vendedorService.alterarVendedor(id, vendedorRequest));
        assertEquals("CPF já cadastrado", ex.getMessage());
        verify(vendedorRepository).findById(id);
        verify(vendedorRepository).findByCpf("33311122211");
        verify(vendedorRepository, never()).save(any());
    }

    @Test
    void deveChamarMetodoEditarDoVendedor() {
        Long id = 1L;
        Vendedor vendedorReal = spy(new Vendedor());
        vendedorReal.setCpf("11122233311");
        VendedorRequest vendedorRequest = new VendedorRequest("Luiz Fernando Test", "11122233311");
        when(vendedorRepository.findById(id)).thenReturn(Optional.of(vendedorReal));
        vendedorService.alterarVendedor(id, vendedorRequest);
        verify(vendedorReal).editar(vendedorRequest);
        verify(vendedorRepository).save(vendedorReal);
    }

    @Test
    void deveDeletarQuandoIdExistir() {
        Long id = 1L;
        when(vendedorRepository.existsById(id)).thenReturn(true);
        vendedorService.deletar(id);
        verify(vendedorRepository).existsById(id);
        verify(vendedorRepository).deleteById(id);
    }

    @Test
    void naoDeveChamarDeleteQuandoIdNaoExistir() {
        Long id = 1L;
        when(vendedorRepository.existsById(id)).thenReturn(false);
        assertThrows(
                NegocioException.class,
                () -> vendedorService.deletar(id));
        verify(vendedorRepository, never()).deleteById(any());
    }

    @Test
    void deveChamarExistsByIdUmaVez() {
        Long id = 1L;
        when(vendedorRepository.existsById(id)).thenReturn(true);
        vendedorService.deletar(id);
        verify(vendedorRepository, times(1)).existsById(id);
    }

    @Test
    void deveChamarDeleteByIdUmaVez() {
        Long id = 1L;
        when(vendedorRepository.existsById(id)).thenReturn(true);
        vendedorService.deletar(id);
        verify(vendedorRepository, times(1)).deleteById(id);
    }

    @Test
    void deveLancarErroQuandoIdForNegativoNoDelete() {
        assertThrows(NegocioException.class, () -> vendedorService.deletar(-1L));
    }
}