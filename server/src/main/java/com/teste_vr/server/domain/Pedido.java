package com.teste_vr.server.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Classe que representa um Pedido.
 */
@Entity
@Table(name = "pedido")
public class Pedido {
    /**
     * ID único do Pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Código do Pedido.
     */
    @Column(nullable = false)
    private Long codigo;

    /**
     * Cliente que realizou o Pedido.
     */
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    /**
     * Lista de Itens do Pedido.
     */
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ItemPedido> itens;

    /**
     * Valor Total do Pedido.
     */
    @Column(nullable = false)
    private BigDecimal valorTotal;

    /**
     * Construtor sem argumentos.
     */
    public Pedido() {
    }

    /**
     * Retorna o ID do Pedido.
     *
     * @return O ID do Pedido.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do Pedido.
     *
     * @param id o ID do Pedido.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna o {@link Cliente} do Pedido.
     *
     * @return O {@link Cliente} do Pedido.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Define o {@link Cliente} do Pedido.
     *
     * @param cliente O {@link Cliente} do Pedido.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna a {@link List} de {@link ItemPedido} do Pedido.
     *
     * @return A {@link List} de {@link ItemPedido} do Pedido.
     */
    public List<ItemPedido> getItens() {
        return itens;
    }

    /**
     * Define a {@link List} de {@link ItemPedido} do Pedido.
     *
     * @param itens A {@link List} de {@link ItemPedido} do Pedido.
     */
    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    /**
     * Retorna o Valor Total do Pedido.
     *
     * @return O Valor Total do Pedido
     */
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    /**
     * Define o Valor Total do Pedido.
     *
     * @param valorTotal O Valor Total do Pedido
     */
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    /**
     * Retorna o código do Pedido.
     *
     * @return O Código do Pedido.
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Define o código do Pedido.
     *
     * @param codigo O Código do Pedido.
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
}
