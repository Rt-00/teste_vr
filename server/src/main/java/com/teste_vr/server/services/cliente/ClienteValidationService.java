package com.teste_vr.server.services.cliente;

import com.teste_vr.server.dtos.cliente.CreateClienteDTO;
import com.teste_vr.server.dtos.cliente.UpdateClienteDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service responsável pela validação de dados do cliente.
 */
@Service
public class ClienteValidationService {

    /**
     * Valida o DTO de criação de Cliente.
     *
     * @param createClienteDTO O {@link CreateClienteDTO} contendo os dados do Cliente a ser validado.
     * @throws IllegalArgumentException Se algum dado do Cliente for inválido.
     */
    public void validarClienteClienteDTO(CreateClienteDTO createClienteDTO) {
        if (createClienteDTO.nome() == null || createClienteDTO.nome().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }
        if (createClienteDTO.limiteCompra() == null || createClienteDTO.limiteCompra().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O limite de compra não pode ser nulo ou negativo.");
        }
    }

    /**
     * Valida o DTO de atualização de Cliente.
     *
     * @param updateClienteDTO O {@link UpdateClienteDTO} contendo os dados do Cliente a ser validado.
     * @throws IllegalArgumentException Se algum dado do Cliente for inválido.
     */
    public void validarUpdateClienteDTO(UpdateClienteDTO updateClienteDTO, Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        if (updateClienteDTO.nome() != null && updateClienteDTO.nome().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }

        if (updateClienteDTO.limiteCompra() != null && updateClienteDTO.limiteCompra().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O limite de compra não pode ser negativo.");
        }
    }
}
