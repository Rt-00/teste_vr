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
        validarNome(createClienteDTO.nome());
        validarLimiteCompra(createClienteDTO.limiteCompra());
    }

    /**
     * Valida o DTO de atualização de Cliente.
     *
     * @param updateClienteDTO O {@link UpdateClienteDTO} contendo os dados do Cliente a ser validado.
     * @throws IllegalArgumentException Se algum dado do Cliente for inválido.
     */
    public void validarUpdateClienteDTO(UpdateClienteDTO updateClienteDTO) {
        validarId(updateClienteDTO.id());
        validarNome(updateClienteDTO.nome());
        validarLimiteCompra(updateClienteDTO.limiteCompra());
    }

    /**
     * Valida o ID do Cliente.
     *
     * @param id O ID único do Cliente.
     * @throws IllegalArgumentException Se o ID for nulo ou menor que zero.
     */
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
    }

    /**
     * Valida o nome do Cliente.
     *
     * @param nome O Nome do Cliente.
     * @throws IllegalArgumentException Se o Nome do Cliente for nulo ou vazio.
     */
    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O Nome é obrigatório.");
        }
    }

    /**
     * Valida o Limite de Compra do Cliente.
     *
     * @param limiteCompra O Limite de Compra do Cliente.
     * @throws IllegalArgumentException Se o Limite de Compra do Cliente menor ou igual a zero.
     */
    private void validarLimiteCompra(BigDecimal limiteCompra) {
        if (limiteCompra.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O Limite de Compra deve ser maior ou igual a zero.");
        }
    }
}
