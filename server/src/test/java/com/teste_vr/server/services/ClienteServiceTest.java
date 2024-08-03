package com.teste_vr.server.services;

import com.teste_vr.server.domain.Cliente;
import com.teste_vr.server.dtos.cliente.CreateClienteDTO;
import com.teste_vr.server.dtos.cliente.ListClienteDTO;
import com.teste_vr.server.dtos.cliente.UpdateClienteDTO;
import com.teste_vr.server.mappers.ClienteMapper;
import com.teste_vr.server.repositories.ClienteRepository;
import com.teste_vr.server.services.cliente.ClienteService;
import com.teste_vr.server.services.cliente.ClienteValidationService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o {@link ClienteService}
 */
public class ClienteServiceTest {
    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @Mock
    private ClienteValidationService clienteValidationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o método {@link ClienteService#salvarCliente(CreateClienteDTO)}.
     * Verifica se o Cliente é salvo corretamente e se o DTO retornado está correto.
     */
    @Test
    public void testSalvarCliente() {
        CreateClienteDTO createClientDTO = new CreateClienteDTO("Teste", new BigDecimal("1000.00"));

        Cliente cliente = new Cliente();
        cliente.setNome("Teste");
        cliente.setLimiteCompra(new BigDecimal("1000.00"));

        ListClienteDTO clientDTO = new ListClienteDTO(1L, "Teste", new BigDecimal("1000.00"));

        when(clienteMapper.toEntity(createClientDTO)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteMapper.toListClienteDTO(cliente)).thenReturn(clientDTO);

        ListClienteDTO resultado = clienteService.salvarCliente(createClientDTO);

        assertNotNull(resultado);
        assertEquals("Teste", resultado.nome());
        assertEquals(new BigDecimal("1000.00"), resultado.limiteCompra());
    }

    /**
     * Testa o método {@link ClienteService#atualizarCliente(UpdateClienteDTO, Long)}.
     * Verifica se o Cliente é atualizado corretamente e se o DTO retornado está correto.
     */
    @Test
    public void testAtualizarCliente() {
        Long clientAttId = 1L;
        UpdateClienteDTO updateClienteDTO = new UpdateClienteDTO("Teste", new BigDecimal("1000.0"));

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Teste");
        cliente.setLimiteCompra(new BigDecimal("1000.00"));

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setId(1L);
        clienteAtualizado.setNome("Teste Atualizado");
        clienteAtualizado.setLimiteCompra(new BigDecimal("2000.0"));

        ListClienteDTO listClienteDTO = new ListClienteDTO(1L, "Teste Atualizado",
                new BigDecimal("2000.0"));

        when(clienteRepository.findById(1L)).thenReturn(java.util.Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(clienteAtualizado);
        when(clienteMapper.toListClienteDTO(clienteAtualizado)).thenReturn(listClienteDTO);

        ListClienteDTO resultado = clienteService.atualizarCliente(updateClienteDTO, clientAttId);

        assertNotNull(resultado, "O resultado não deve ser nulo.");
        assertEquals("Teste Atualizado", resultado.nome(),
                "O nome atualizado do cliente deve ser 'Teste Atualizado'.");
        assertEquals(new BigDecimal("2000.0"), resultado.limiteCompra(),
                "O limite de compra atualizado do cliente deve ser 2000.0.");
    }

    /**
     * Testa o método {@link ClienteService#atualizarCliente(UpdateClienteDTO, Long)}.
     * Verifica se uma exceção é lançada quando o cliente não é encontrado.
     */
    @Test
    public void testAtualizarClienteClienteNaoEncontrado() {
        Long clientAttId = 1L;
        UpdateClienteDTO updateClienteDTO = new UpdateClienteDTO("Teste", new BigDecimal("1000.0"));

        when(clienteRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class,
                () -> clienteService.atualizarCliente(updateClienteDTO, clientAttId),
                "Deveria lançar uma exceção quando o cliente não é encontrado.");

        assertEquals("Cliente não encontrado.", exception.getMessage(),
                "A mensagem da exceção deve indicar que o cliente não foi encontrado.");
    }

    /**
     * Testa o método {@link ClienteService#excluirCliente(Long)}.
     * Verifica se o cliente é excluído corretamente quando o cliente existe.
     */
    @Test
    public void testeExcluirCliente() {
        Long clienteId = 1L;

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNome("Teste");
        cliente.setLimiteCompra(new BigDecimal("1000.0"));

        when(clienteRepository.existsById(clienteId)).thenReturn(true);
        when(clienteRepository.findById(clienteId)).thenReturn(java.util.Optional.of(cliente));

        assertDoesNotThrow(() -> clienteService.excluirCliente(clienteId),
                "Não deve lançar exceção ao deletar um cliente existente.");

        verify(clienteRepository, times(1)).deleteById(clienteId);
    }

    /**
     * Testa o método {@link ClienteService#excluirCliente(Long)}.
     * Verifica se uma exceção é lançada quando o cliente não é encontrado.
     */
    @Test
    public void testExcluirClienteClienteNaoEncontrado() {
        Long clienteId = 1L;

        when(clienteRepository.existsById(clienteId)).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> clienteService.excluirCliente(clienteId),
                "Deveria lançar uma exceção quando o cliente não é encontrado.");

        assertEquals("Cliente não encontrado", exception.getMessage(),
                "A mensagem da exceção deve indicar que o cliente não foi encontrado.");
    }

}
