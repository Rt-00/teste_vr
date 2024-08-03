package com.teste_vr.server.dtos.cliente;

import com.teste_vr.server.domain.Fatura;
import com.teste_vr.server.dtos.fatura.CreateFaturaDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para criação de Clientes.
 *
 * @param nome         O Nome do Cliente.
 * @param limiteCompra O Limite de Compra do Cliente.
 */
public record CreateClienteDTO(
        String nome,
        BigDecimal limiteCompra
) {
}
