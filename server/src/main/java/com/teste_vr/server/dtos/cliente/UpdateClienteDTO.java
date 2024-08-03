package com.teste_vr.server.dtos.cliente;

import java.math.BigDecimal;

/**
 * DTO para atualização de Clientes.
 *
 * @param codigo       O Código do Cliente.
 * @param nome         O Nome do Cliente.
 * @param limiteCompra O Limite de Compra do Cliente.
 */
public record UpdateClienteDTO(
        Long codigo,
        String nome,
        BigDecimal limiteCompra
) {
}
