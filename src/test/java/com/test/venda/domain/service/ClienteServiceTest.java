package com.test.venda.domain.service;

import com.test.venda.api.dto.request.ClienteRequest;
import com.test.venda.api.dto.response.ClienteResponse;
import com.test.venda.api.mappers.ClienteMapper;
import com.test.venda.domain.entity.Cliente;
import com.test.venda.domain.exceptions.NegocioException;
import com.test.venda.domain.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void deveFalharQuandoCpfInvalido() {
        ClienteRequest clienteRequest = new ClienteRequest("Luiz Fernando", " ");
        assertThrows(NegocioException.class, ()-> clienteService.salvar(clienteRequest));
    }

    @Test
    void deveFalharQuandoNomeVazio() {
        ClienteRequest clienteRequest = new ClienteRequest("", "12345678901");
        assertThrows(NegocioException.class, () -> clienteService.salvar(clienteRequest));
    }

    @Test
    void deveFalharQuandoCpfContemLetras() {
        ClienteRequest clienteRequest = new ClienteRequest("Luiz", "11A222B3311");
        assertThrows(NegocioException.class, () -> clienteService.salvar(clienteRequest));
    }

    @Test
    void deveFalharQuandoCpfMenorQue11() {
        ClienteRequest clienteRequest = new ClienteRequest("Luiz", "1234567890");
        assertThrows(NegocioException.class, () -> clienteService.salvar(clienteRequest));
    }

    @Test
    void deveFalharQuandoCpfMaiorQue11() {
        ClienteRequest clienteRequest = new ClienteRequest("Luiz", "123456789012345");
        assertThrows(NegocioException.class, () -> clienteService.salvar(clienteRequest));
    }

    @Test
    void deveFalharQuandoCpfjaExiste(){
        ClienteRequest clienteRequest = new ClienteRequest("Luiz Fernando","11122233311");
        when(clienteRepository.findByCpf("11122233311"))
                .thenReturn(Optional.of(new Cliente()));
        NegocioException e = assertThrows(NegocioException.class,
                ()-> clienteService.salvar(clienteRequest));
        assertEquals("CPF já cadastrado", e.getMessage());
    }

    @Test
    void deveSalvarClienteComSucesso(){
        ClienteRequest clienteRequest = new ClienteRequest("Luiz Fernando","11122233311");
        Cliente entity = new Cliente();
        ClienteResponse clienteResponse = new ClienteResponse();
        when(clienteRepository.findByCpf("11122233311"))
                .thenReturn(Optional.empty());
        when(clienteMapper.toEntity(clienteRequest))
                .thenReturn(entity);
        when(clienteRepository.save(entity))
                .thenReturn(entity);
        when(clienteMapper.toModel(entity))
                .thenReturn(clienteResponse);
        ClienteResponse devolve = clienteService.salvar(clienteRequest);
        verify(clienteRepository).save(entity);
    }

    @Test
    void deveNormalizarCpfAntesDeSalvar(){
        ClienteRequest clienteRequest = new ClienteRequest("Luiz Fernando","111.222.333.11");
        Cliente entity = new Cliente();
        ClienteResponse clienteResponse = new ClienteResponse();
        when(clienteRepository.findByCpf("11122233311"))
                .thenReturn(Optional.empty());
        when(clienteMapper.toEntity(clienteRequest))
                .thenReturn((entity));
        when(clienteRepository.save(entity))
                .thenReturn(entity);
        clienteService.salvar(clienteRequest);
        assertEquals("11122233311",entity.getCpf());
    }

    @Test
    void deveChamarMapperParaConversao(){
        ClienteRequest clienteRequest = new ClienteRequest("Luiz Fernando","11122233311");
        Cliente entity = new Cliente();
        ClienteResponse clienteResponse = new ClienteResponse();
        when(clienteRepository.findByCpf("11122233311"))
                .thenReturn(Optional.empty());
        when(clienteMapper.toEntity(clienteRequest))
                .thenReturn((entity));
        when(clienteRepository.save(entity))
                .thenReturn(entity);
        clienteService.salvar(clienteRequest);
        assertEquals("11122233311",entity.getCpf());
        verify(clienteMapper).toEntity(clienteRequest);
        verify(clienteMapper).toModel(entity);
    }

    @Test
    void deveRetornarListaDeClientes(){
        var cliente = new Cliente();
        List<Cliente> clientes = List.of(cliente);
        var response = new ClienteResponse();
        List<ClienteResponse> responses = List.of(response);
        when(clienteRepository.findAll())
                .thenReturn(clientes);
        when(clienteMapper.toColletionModel(clientes))
                .thenReturn(responses);
        List<ClienteResponse> resultado = clienteService.listarClientes();
        assertEquals(1,resultado.size());
        verify(clienteRepository).findAll();
        verify(clienteMapper).toColletionModel(clientes);
    }

    @Test
    void deveRetornarListaVazia(){
        when(clienteRepository.findAll()).thenReturn(List.of());
        when(clienteMapper.toColletionModel(List.of())).thenReturn(List.of());
        List<ClienteResponse> resultado =clienteService.listarClientes();
        assertTrue(resultado.isEmpty());
    }

    @Test
    void develancarErroSeRepositoryFalhar() {
        when(clienteRepository.findAll())
                .thenThrow(new NegocioException("Erro nobanco"));
        assertThrows(NegocioException.class,()-> clienteService.listarClientes());
    }

    @Test
    void deveLancarErroSeMapperFalhar(){
        when(clienteRepository.findAll())
                .thenReturn(List.of(new Cliente()));
        when(clienteMapper.toColletionModel(Mockito.anyList()))
                .thenThrow(new NegocioException("Erro no Mapper"));
        assertThrows(RuntimeException.class, () -> clienteService.listarClientes());
    }

    @Test
    void deveRetornarClienteQuandoIdExistir(){
        Long id = 1L;
        Cliente cliente = new Cliente();
        ClienteResponse clienteResponse = new ClienteResponse();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toModel(cliente)).thenReturn(clienteResponse);
        ClienteResponse resultado = clienteService.buscarPorId(id);
        assertNotNull(resultado);
        assertEquals(clienteResponse, resultado);
        verify(clienteRepository).findById(id);
        verify(clienteMapper).toModel(cliente);
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoExistir() {
        Long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());
        NegocioException exception = assertThrows(NegocioException.class,
                () -> clienteService.buscarPorId(id));
        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(clienteRepository).findById(id);
        verify(clienteMapper, never()).toModel(any());
    }

    @Test
    void deveLancarExcecaoQuandoIdForNulo() {
        assertThrows(NegocioException.class, () -> clienteService.buscarPorId(null));
    }

    @Test
    void naoDeveChamarMapperQuandoClienteNaoExistir() {
        Long id = 10L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NegocioException.class, () -> clienteService.buscarPorId(id));
        verify(clienteMapper, never()).toModel(any());
    }

    @Test
    void naoDeveValidarDuplicidadeQuandoCpfForIgual() {
        Long id = 1L;
        Cliente clienteExistente = new Cliente();
        clienteExistente.setCpf("11122233311");
        ClienteRequest clienteRequest = new ClienteRequest("Luiz Fernando", "11122233311");
        when(clienteRepository.findById(id))
                .thenReturn(Optional.of(clienteExistente));
        clienteService.alterarCliente(id, clienteRequest);
        verify(clienteRepository, never()).findByCpf(any());
        verify(clienteRepository).save(clienteExistente);
    }

    @Test
    void deveLancarExcecaoQuandoCpfSerDiferenteAtualizarParaCpfRepetido() {
        Long id = 1L;
        Cliente clienteExistente = new Cliente();
        clienteExistente.setCpf("11111111111");
        ClienteRequest clienteRequest = new ClienteRequest("Luiz Fernando", "33311122211");
        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.findByCpf("33311122211"))
                .thenReturn(Optional.of(new Cliente()));
        NegocioException ex = assertThrows(
                NegocioException.class,
                () -> clienteService.alterarCliente(id, clienteRequest));
        assertEquals("CPF já cadastrado", ex.getMessage());
        verify(clienteRepository).findById(id);
        verify(clienteRepository).findByCpf("33311122211");
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void deveChamarMetodoEditarDoCliente() {
        Long id = 1L;
        Cliente clienteReal = spy(new Cliente());
        clienteReal.setCpf("11122233311");
        ClienteRequest clienteRequest = new ClienteRequest("Luiz Fernando Test", "11122233311");
        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteReal));
        clienteService.alterarCliente(id, clienteRequest);
        verify(clienteReal).editar(clienteRequest);
        verify(clienteRepository).save(clienteReal);
    }

    @Test
    void deveDeletarQuandoIdExistir() {
        Long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(true);
        clienteService.deletar(id);
        verify(clienteRepository).existsById(id);
        verify(clienteRepository).deleteById(id);
    }

    @Test
    void naoDeveChamarDeleteQuandoIdNaoExistir() {
        Long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(false);
        assertThrows(
                NegocioException.class,
                () -> clienteService.deletar(id));
        verify(clienteRepository, never()).deleteById(any());
    }

    @Test
    void deveChamarExistsByIdUmaVez() {
        Long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(true);
        clienteService.deletar(id);
        verify(clienteRepository, times(1)).existsById(id);
    }

    @Test
    void deveChamarDeleteByIdUmaVez() {
        Long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(true);
        clienteService.deletar(id);
        verify(clienteRepository, times(1)).deleteById(id);
    }

    @Test
    void deveLancarErroQuandoIdForNegativoNoDelete() {
        assertThrows(NegocioException.class, () -> clienteService.deletar(-1L));
    }
}