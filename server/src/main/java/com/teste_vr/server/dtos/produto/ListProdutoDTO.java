package com.teste_vr.server.dtos.produto;

import java.math.BigDecimal;

/**
 * DTO para listagem de Produtos.
 *
 * @param codigo    O Código do Produto.
 * @param descricao A Descrição do Produto.
 * @param preco     O Preço do Produto.
 */
public record ListProdutoDTO(
        Long codigo,
        String descricao,
        BigDecimal preco
) {
}
