package com.teste_vr.client.dtos.produto;

import java.math.BigDecimal;

public class ProdutoDTO {

    /**
     * Código do Produto.
     */
    private Long codigo;

    /**
     * Descrição do Produto.
     */
    private String descricao;

    /**
     * Preço do Produto.
     */
    private BigDecimal preco;

    /**
     * Constutor com argumentos.
     *
     * @param codigo    Código do Produto.
     * @param descricao Descrição do Produto.
     * @param preco     Preço do Produto.
     */
    public ProdutoDTO(Long codigo, String descricao, BigDecimal preco) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
    }

    /**
     * Construtor sem argumentos.
     */
    public ProdutoDTO() {
    }

    /**
     * Retorna o código do Produto.
     *
     * @return O código do Produto.
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Define o código do Produto.
     *
     * @param codigo O Código do Produto.
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna a Descrição do Produto.
     *
     * @return A Descrição do Produto
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a Descrição do Produto
     *
     * @param descricao A Descrição do Produto
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o Preço do Produto.
     *
     * @return O Preço do Produto.
     */
    public BigDecimal getPreco() {
        return preco;
    }

    /**
     * Retorna o Preço do Produto.
     *
     * @param preco O Preço do Produto.
     */
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
