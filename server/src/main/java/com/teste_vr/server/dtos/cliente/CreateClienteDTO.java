package com.teste_vr.server.dtos.cliente;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO para criação de Clientes.
 *
 * @param codigo               O Código do Cliente.
 * @param nome                 O Nome do Cliente.
 * @param limiteCompra         O Limite de Compra do Cliente.
 * @param dataFechamentoFatura A Data de Fechamento da Fatura.
 */
public record CreateClienteDTO(
        Long codigo,
        String nome,
        BigDecimal limiteCompra,
        Date dataFechamentoFatura
) {
}
