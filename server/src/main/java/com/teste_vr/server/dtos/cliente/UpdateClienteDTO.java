package com.teste_vr.server.dtos.cliente;

import java.math.BigDecimal;

/**
 * DTO para atualização de Clientes.
 *
 * @param nome         O Nome do Cliente.
 * @param limiteCompra O Limite de Compra do Cliente.
 */
public record UpdateClienteDTO(
        String nome,
        BigDecimal limiteCompra
) {
}
