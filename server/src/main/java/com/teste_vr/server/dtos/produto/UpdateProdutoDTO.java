package com.teste_vr.server.dtos.produto;

import java.math.BigDecimal;

/**
 * DTO para atualização de Produtos.
 *
 * @param codigo    O código do Produto.
 * @param descricao A descrição do Produto.
 * @param preco     O Preço do Produto.
 */
public record UpdateProdutoDTO(
        Long codigo,
        String descricao,
        BigDecimal preco
) {
}
