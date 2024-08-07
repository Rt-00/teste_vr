package com.teste_vr.server.dtos.itempedido;

import com.teste_vr.server.dtos.produto.ListProdutoDTO;

import java.math.BigDecimal;

public record ListItemPedidoDTO(
        Long codigo,
        ListProdutoDTO produto,
        Integer quantidade,
        BigDecimal preco
) {
}
