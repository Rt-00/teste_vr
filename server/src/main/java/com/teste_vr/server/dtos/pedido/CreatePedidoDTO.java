package com.teste_vr.server.dtos.pedido;

import com.teste_vr.server.dtos.cliente.CreateClienteDTO;
import com.teste_vr.server.dtos.itempedido.CreateItemPedidoDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record CreatePedidoDTO(
        Long codigo,
        CreateClienteDTO cliente,
        List<CreateItemPedidoDTO> itensPedido,
        BigDecimal valorTotal
) {
}
