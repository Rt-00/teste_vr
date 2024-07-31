package com.teste_vr.server.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Class que representa uma Fatura.
 */
@Entity
@Table(name = "fatura")
public class Fatura {
    /**
     * ID único da Fatura.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Cliente associado à Fatura.
     */
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    /**
     * Data de Fechamento da Fatura.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime dataFechamento;

    /**
     * Valor total da Fatura.
     */
    @Column(nullable = false)
    private double valorTotal;

    /**
     * Construtor sem argumentos.
     */
    public Fatura() {
    }

    /**
     * Define o ID da Fatura.
     *
     * @return O ID da Fatura.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID da Fatura.
     *
     * @param id O ID da Fatura.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retornar o {@link Cliente} associado à Fatura.
     *
     * @return O {@link Cliente} associado à Fatura.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Define o {@link Cliente} associado à Fatura.
     *
     * @param cliente O {@link Cliente} que será associado à Fatura.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna a Data de Fechamento da Fatura.
     *
     * @return A Data de Fechamento da Fatura.
     */
    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    /**
     * Retorna a Data de Fechamento da Fatura.
     *
     * @param dataFechamento a Data de Fechamento da Fatura.
     */
    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    /**
     * Retorna o Valor Total da Fatura.
     *
     * @return O Valor Total da Fatura.
     */
    public double getValorTotal() {
        return valorTotal;
    }

    /**
     * Define o Valor Total da Fatura.
     *
     * @param valorTotal o Valor Total da Fatura.
     */
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
