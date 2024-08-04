package com.teste_vr.server.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

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
     * Código do Cliente.
     */
    @Column(nullable = false, unique = true)
    private Long codigo;

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

    @Column(nullable = false)
    private Date dataFechamentoFatura;

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
     * Retorna o código do Cliente.
     *
     * @return O Código do Cliente.
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Define o código do Cliente.
     *
     * @param codigo O código do Cliente.
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna a Data de Fechamento da Fatura.
     *
     * @return A Data de Fechamento da Fatura.
     */
    public Date getDataFechamentoFatura() {
        return dataFechamentoFatura;
    }

    /**
     * Define a Data de Fechamento da Fatura.
     *
     * @param dataFechamentoFatura A Data de Fechamento da Fatura.
     */
    public void setDataFechamentoFatura(Date dataFechamentoFatura) {
        this.dataFechamentoFatura = dataFechamentoFatura;
    }
}
