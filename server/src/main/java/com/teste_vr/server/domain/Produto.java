package com.teste_vr.server.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * Classe que representa um Produto.
 */
@Entity
@Table(name = "produto")
public class Produto {
    /**
     * ID único do Produto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Descrição do Produto.
     */
    @Column(nullable = false)
    private String descricao;

    /**
     * Preco do Produto.
     */
    @Column(nullable = false)
    private BigDecimal preco;

    /**
     * Construtor sem argumentos.
     */
    public Produto() {
    }

    /**
     * Retorna o ID do Produto.
     *
     * @return O ID do Produto.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do Produto.
     *
     * @param id O ID do Produto.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna a Descrição do Produto.
     *
     * @return a Descrição do Produto.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a Descrição do Produto.
     *
     * @param descricao A Descrição do Produto.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o Preço do Produto.
     *
     * @return o Preço do Produto.
     */
    public BigDecimal getPreco() {
        return preco;
    }

    /**
     * Define o Preço do Produto.
     *
     * @param preco o Preço do Produto.
     */
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
