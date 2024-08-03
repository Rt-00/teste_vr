package com.teste_vr.server.services;

import com.teste_vr.server.dtos.cliente.CreateClienteDTO;
import com.teste_vr.server.dtos.cliente.UpdateClienteDTO;
import com.teste_vr.server.services.cliente.ClienteValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteValidationServiceTest {

    private ClienteValidationService clienteValidationService;

    @BeforeEach
    void setUp() {
        clienteValidationService = new ClienteValidationService();
    }

    @Test
    public void testValidarClienteDTOSemNome() {
        CreateClienteDTO createClientDTO = new CreateClienteDTO(1L, null, new BigDecimal("1000.0"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteValidationService.validarClienteClienteDTO(createClientDTO));
        assertEquals("O nome não pode ser nulo ou vazio.", exception.getMessage());
    }

    @Test
    public void testValidarClienteDTOSemCodigo() {
        CreateClienteDTO createClientDTO = new CreateClienteDTO(null, "Cliente", new BigDecimal("1000.0"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteValidationService.validarClienteClienteDTO(createClientDTO));
        assertEquals("O código não pode ser nulo ou vazio.", exception.getMessage());
    }

    @Test
    public void testValidarClienteDTOSemLimiteCompra() {
        CreateClienteDTO createClientDTO = new CreateClienteDTO(1L, "Cliente", null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteValidationService.validarClienteClienteDTO(createClientDTO));
        assertEquals("O limite de compra não pode ser nulo ou negativo.", exception.getMessage());
    }

    @Test
    public void testValidarUpdateClienteDTONomeVazio() {
        UpdateClienteDTO updateClientDTO = new UpdateClienteDTO(1L, "", new BigDecimal("1000.0"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteValidationService.validarUpdateClienteDTO(updateClientDTO, 1L));
        assertEquals("O nome não pode ser nulo ou vazio.", exception.getMessage());
    }

    @Test
    public void testValidarUpdateClienteDTOLimiteCompraInvalido() {
        UpdateClienteDTO updateClienteDTO = new UpdateClienteDTO(1L, "Cliente", new BigDecimal("-100.0"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteValidationService.validarUpdateClienteDTO(updateClienteDTO, 1L));
        assertEquals("O limite de compra não pode ser nulo ou negativo.", exception.getMessage());
    }
}
