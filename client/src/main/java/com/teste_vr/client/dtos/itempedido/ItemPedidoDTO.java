package com.teste_vr.client.dtos.itempedido;

import com.teste_vr.client.dtos.produto.ProdutoDTO;

import java.math.BigDecimal;

public class ItemPedidoDTO {
    private Long codigo;
    private ProdutoDTO produto;
    private int quantidade;
    private BigDecimal preco;

    public ItemPedidoDTO() {
    }

    public ItemPedidoDTO(Long codigo, ProdutoDTO produto, int quantidade, BigDecimal preco) {
        this.codigo = codigo;
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
}
