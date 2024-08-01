package com.teste_vr.server.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Classe que representa um Cliente.
 */
@Entity
@Table(name = "cliente")
public class Cliente {
    /**
     * ID único do Cliente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do Cliente.
     */
    @Column(nullable = false)
    private String nome;

    /**
     * Limite de Compra do Cliente.
     */
    @Column(nullable = false)
    private BigDecimal limiteCompra;

    /**
     * Lista de Faturas do Cliente.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fatura> faturas;

    /**
     * Construtor sem argumentos.
     */
    public Cliente() {
    }

    /**
     * Retorna o ID do Cliente.
     *
     * @return O ID do Cliente.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do Cliente.
     *
     * @param id O ID do Cliente.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna o Nome do Cliente.
     *
     * @return O Nome do Cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o Nome do Cliente.
     *
     * @param nome O Nome do Cliente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o Limite de Compra do Cliente.
     *
     * @return O Limite de Compra do Cliente.
     */
    public BigDecimal getLimiteCompra() {
        return limiteCompra;
    }

    /**
     * Defina o Limite de Compra do Cliente.
     *
     * @param limiteCompra O Limite de Compra do Cliente.
     */
    public void setLimiteCompra(BigDecimal limiteCompra) {
        this.limiteCompra = limiteCompra;
    }

    /**
     * Retorna a {@link List} de {@link Fatura} do Cliente.
     *
     * @return A {@link List} de {@link Fatura} do Cliente.
     */
    public List<Fatura> getFaturas() {
        return faturas;
    }

    /**
     * Define a {@link List} de Faturas do Cliente.
     *
     * @param faturas A {@link List} de Faturas do Cliente.
     */
    public void setFaturas(List<Fatura> faturas) {
        this.faturas = faturas;
    }
}
