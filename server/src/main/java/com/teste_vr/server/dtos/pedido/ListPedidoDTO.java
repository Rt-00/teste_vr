package com.teste_vr.server.dtos.pedido;

import com.teste_vr.server.dtos.cliente.ListClienteDTO;
import com.teste_vr.server.dtos.itempedido.ListItemPedidoDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record ListPedidoDTO(
        Long codigo,
        ListClienteDTO cliente,
        List<ListItemPedidoDTO> itensPedido,
        BigDecimal valorTotal
) {
}
