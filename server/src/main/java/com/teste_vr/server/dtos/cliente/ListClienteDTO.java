package com.teste_vr.server.dtos.cliente;

import java.math.BigDecimal;

/**
 * DTO para listagem de Clientes.
 *
 * @param codigo       O Código do Cliente.
 * @param nome         O Nome do Cliente.
 * @param limiteCompra O Limite de Compra do Cliente.
 */
public record ListClienteDTO(
        Long codigo,
        String nome,
        BigDecimal limiteCompra
) {
}
