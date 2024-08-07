package com.teste_vr.client.dtos.clientes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe para representar um Cliente.
 */
public class ClienteDTO {

    /**
     * Construtur com argumentos.
     *
     * @param codigo       O Código do Cliente.
     * @param nome         O Nome do Cliente.
     * @param limiteCompra O Limite de Compra do Cliente.
     */
    public ClienteDTO(Long codigo, String nome, BigDecimal limiteCompra, Date dataFechamentoFatura) {
        this.codigo = codigo;
        this.nome = nome;
        this.limiteCompra = limiteCompra;
        this.dataFechamentoFatura = dataFechamentoFatura;
    }

    /**
     * Construtor sem argumentos.
     */
    public ClienteDTO() {
    }

    /**
     * Código do Cliente.
     */
    private Long codigo;

    /**
     * Nome do Cliente.
     */
    private String nome;

    /**
     * Limite de Compra do Cliente.
     */
    private BigDecimal limiteCompra;

    /**
     * Data de fechamento da fatura.
     */
    private Date dataFechamentoFatura;

    /**
     * Retorna a Data de Fechamento da Fatura.
     *
     * @return A Data de Fechamento da Fatura.
     */
    public Date getDataFechamentoFatura() {
        return dataFechamentoFatura;
    }

    /**
     * Defina a Data de Fechamento da Fatura.
     *
     * @param dataFechamentoFatura A Data de Fechamento da Fatura.
     */
    public void setDataFechamentoFatura(Date dataFechamentoFatura) {
        this.dataFechamentoFatura = dataFechamentoFatura;
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

    @Override
    public String toString() {
        return codigo + " - " + nome;
    }
}
