package com.teste_vr.server.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * Classe que representa um Item de Pedido.
 */
@Entity
@Table(name = "itempedido")
public class ItemPedido {
    /**
     * ID único do Item de Pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long codigo;

    /**
     * Pedido que contém esse Item de Pedido.
     */
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = true)
    private Pedido pedido;

    /**
     * Produto que representa esse Item de Pedido.
     */
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = true)
    private Produto produto;

    /**
     * Quantidade de Item de Pedido.
     */
    @Column(nullable = false)
    private int quantidade;

    /**
     * Preço do Item de Pedido.
     */
    @Column(nullable = false)
    private BigDecimal preco;

    /**
     * Construtor sem argumentos.
     */
    public ItemPedido() {
    }

    /**
     * Retorna o ID do Item de Pedido.
     *
     * @return O ID do Item de Pedido.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do Item de Pedido.
     *
     * @param id o ID do Item de Pedido.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna o {@link Pedido} que contém este Item de Pedido.
     *
     * @return O {@link Pedido} que contém este Item de Pedido.
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * Define o {@link Pedido} que contém este Item de Pedido.
     *
     * @param pedido O {@link Pedido} que contém este Item de Pedido.
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    /**
     * Retorna o Produto que representa o Item de Pedido.
     *
     * @return O Produto que representa o Item de Pedido.
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Define o {@link Produto} que representa o Item de Pedido.
     *
     * @param produto O {@link Produto} que representa o Item de Pedido.
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * Retorna a Quantidade de Itens de Pedido.
     *
     * @return A Quantidade de Itens de Pedido.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a Quantidade de Itens de Pedido.
     *
     * @param quantidade A Quantidade de Itens de Pedido.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Retorna o Preço do Item de Pedido.
     *
     * @return o Preço do Item de Pedido.
     */
    public BigDecimal getPreco() {
        return preco;
    }

    /**
     * Define o Preço do Item de Pedido.
     *
     * @param preco O Preço do Item de Pedido.
     */
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
