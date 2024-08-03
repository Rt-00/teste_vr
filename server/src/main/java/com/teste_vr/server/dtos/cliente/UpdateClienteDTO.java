package com.teste_vr.server.dtos.cliente;

import java.math.BigDecimal;

/**
 * DTO para atualização de Clientes.
 *
 * @param id           O ID do Cliente.
 * @param nome         O Nome do Cliente.
 * @param limiteCompra O Limite de Compra do Cliente.
 */
public record UpdateClienteDTO(
        Long id,
        String nome,
        BigDecimal limiteCompra
) {
}
