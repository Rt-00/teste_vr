package com.teste_vr.server.services;

import com.teste_vr.server.domain.Cliente;
import com.teste_vr.server.dtos.cliente.CreateClienteDTO;
import com.teste_vr.server.dtos.cliente.ListClienteDTO;
import com.teste_vr.server.dtos.cliente.UpdateClienteDTO;
import com.teste_vr.server.mappers.ClienteMapper;
import com.teste_vr.server.repositories.ClienteRepository;
import com.teste_vr.server.services.cliente.ClienteService;
import com.teste_vr.server.services.cliente.ClienteValidationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
     * Testa o método {@link ClienteService#listarClientes()}.
     * Verifica se é retornado todos os Clientes cadastrados.
     */
    @Test
    public void testListarTodosClientes() {
        ListClienteDTO clienteDTO1 = new ListClienteDTO(1L, "Cliente 1", new BigDecimal("1000.0"));
        ListClienteDTO clienteDTO2 = new ListClienteDTO(2L, "Cliente 2", new BigDecimal("2000.0"));

        List<ListClienteDTO> clientesDtos = Arrays.asList(clienteDTO1, clienteDTO2);

        Cliente cliente1 = new Cliente();
        cliente1.setNome("Cliente 1");
        cliente1.setLimiteCompra(new BigDecimal("1000.0"));

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Cliente 1");
        cliente2.setLimiteCompra(new BigDecimal("1000.0"));

        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        when(clienteRepository.findAll()).thenReturn(clientes);
        when(clienteMapper.toListClienteDTO(cliente1)).thenReturn(clienteDTO1);
        when(clienteMapper.toListClienteDTO(cliente2)).thenReturn(clienteDTO2);

        List<ListClienteDTO> resultado = clienteService.listarClientes();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Cliente 1", resultado.get(0).nome());
        assertEquals("Cliente 2", resultado.get(1).nome());
    }

    /**
     * Testa o método {@link ClienteService#listarClientes()}.
     * Verifica se é retornado todos os Clientes cadastrados.
     */
    @Test
    public void testListarClientePorCodigo() {
        ListClienteDTO clienteDTO1 = new ListClienteDTO(1L, "Cliente 1", new BigDecimal("1000.0"));

        Cliente cliente1 = new Cliente();
        cliente1.setCodigo(1L);
        cliente1.setNome("Cliente 1");
        cliente1.setLimiteCompra(new BigDecimal("1000.0"));

        when(clienteRepository.findByCodigo(1L)).thenReturn(Optional.of(cliente1));
        when(clienteMapper.toListClienteDTO(cliente1)).thenReturn(clienteDTO1);

        ListClienteDTO resultado = clienteService.obterClientePorCodigo(1L);

        assertNotNull(resultado);
        assertEquals("Cliente 1", resultado.nome());
        assertEquals(new BigDecimal("1000.0"), resultado.limiteCompra());
    }

    /**
     * Testa o método {@link ClienteService#salvarCliente(CreateClienteDTO)}.
     * Verifica se o Cliente é salvo corretamente e se o DTO retornado está correto.
     */
    @Test
    public void testSalvarCliente() {
        CreateClienteDTO createClientDTO = new CreateClienteDTO(123L, "Teste", new BigDecimal("1000.00"));

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
     * Testa o método {@link ClienteService#salvarCliente(CreateClienteDTO)}.
     * Verifica se uma exceção é lançada quando já existe um cliente com o código cadastrado.
     */
    @Test
    public void testSalvarClienteCodigoJaCadastrado() {
        // Configuração
        Long codigoCliente = 1L;
        CreateClienteDTO createClientDTO = new CreateClienteDTO(codigoCliente, "Novo Cliente",
                new BigDecimal("1000.0"));

        when(clienteRepository.findByCodigo(codigoCliente)).thenReturn(Optional.of(new Cliente()));

        // Ação e Validação
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class,
                () -> clienteService.salvarCliente(createClientDTO));

        assertEquals("Cliente com o código: " + codigoCliente + " já cadastrado.",
                exception.getMessage());
    }

    /**
     * Testa o método {@link ClienteService#atualizarCliente(UpdateClienteDTO, Long)}.
     * Verifica se o Cliente é atualizado corretamente e se o DTO retornado está correto.
     */
    @Test
    public void testAtualizarCliente() {
        UpdateClienteDTO updateClientDTO = new UpdateClienteDTO(123L, "Nome Atualizado",
                BigDecimal.valueOf(2000.0));

        Cliente cliente = new Cliente();
        cliente.setCodigo(1L);
        cliente.setNome("Teste");
        cliente.setLimiteCompra(new BigDecimal("1000.0"));

        ListClienteDTO clientDTO = new ListClienteDTO(123L, "Nome Atualizado", BigDecimal.valueOf(2000.0));

        when(clienteRepository.findByCodigo(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteMapper.toListClienteDTO(cliente)).thenReturn(clientDTO);

        ListClienteDTO resultado = clienteService.atualizarCliente(updateClientDTO, cliente.getCodigo());

        assertNotNull(resultado);
        assertEquals(123L, resultado.codigo());
        assertEquals("Nome Atualizado", resultado.nome());
        assertEquals(BigDecimal.valueOf(2000.0), resultado.limiteCompra());
    }

    /**
     * Testa o método {@link ClienteService#atualizarCliente(UpdateClienteDTO, Long)}.
     * Verifica se uma exceção é lançada quando o cliente não é encontrado.
     */
    @Test
    public void testAtualizarClienteClienteNaoEncontrado() {
        Long clientAttId = 1L;
        UpdateClienteDTO updateClienteDTO = new UpdateClienteDTO(123L, "Teste",
                new BigDecimal("1000.0"));

        when(clienteRepository.findByCodigo(clientAttId)).thenReturn(java.util.Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> clienteService.atualizarCliente(updateClienteDTO, clientAttId),
                "Deveria lançar uma exceção quando o cliente não é encontrado.");

        assertEquals("Cliente não encontrado.", exception.getMessage(),
                "A mensagem da exceção deve indicar que o cliente não foi encontrado.");
    }

    /**
     * Testa o método {@link ClienteService#atualizarCliente(UpdateClienteDTO, Long)}.
     * <p>
     * Verifica se uma exceção é lançada quando já existe um cliente com o código cadastrado.
     */
    @Test
    public void testAtualizarClienteCodigoJaCadastrado() {
        // Configuração
        Long codigoCliente = 1L;
        UpdateClienteDTO updateClientDTO = new UpdateClienteDTO(codigoCliente, "Cliente Atualizado",
                BigDecimal.valueOf(2000.0));

        when(clienteRepository.findByCodigo(codigoCliente)).thenReturn(Optional.of(new Cliente()));
        when(clienteRepository.findByCodigo(codigoCliente)).thenReturn(Optional.of(new Cliente())); // Simula que já existe um cliente com o mesmo código

        // Ação e Validação
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () ->
                clienteService.atualizarCliente(updateClientDTO, codigoCliente));

        assertEquals("Cliente com o código: " + codigoCliente + " já cadastrado.",
                exception.getMessage());
    }

    /**
     * Testa o método {@link ClienteService#excluirCliente(Long)}.
     * Verifica se o cliente é excluído corretamente quando o cliente existe.
     */
    @Test
    public void testeExcluirCliente() {
        // Configuração
        Long codigoCliente = 1L;
        Cliente cliente = new Cliente();
        cliente.setCodigo(codigoCliente);

        when(clienteRepository.findByCodigo(codigoCliente)).thenReturn(Optional.of(cliente));

        // Ação
        clienteService.excluirCliente(codigoCliente);

        // Validação
        verify(clienteRepository, times(1)).delete(cliente);
    }

    /**
     * Testa o método {@link ClienteService#excluirCliente(Long)}.
     * Verifica se uma exceção é lançada quando o cliente não é encontrado.
     */
    @Test
    public void testExcluirClienteClienteNaoEncontrado() {
        Long clienteCodigo = 1L;

        when(clienteRepository.existsById(clienteCodigo)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> clienteService.excluirCliente(clienteCodigo),
                "Deveria lançar uma exceção quando o cliente não é encontrado.");

        assertEquals("Cliente não encontrado", exception.getMessage(),
                "A mensagem da exceção deve indicar que o cliente não foi encontrado.");
    }

}
