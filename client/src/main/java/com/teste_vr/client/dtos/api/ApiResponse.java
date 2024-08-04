package com.teste_vr.client.dtos.api;

/**
 * Classe que representa uma resposta de uma chamada ao Servidor.
 *
 * @param <T> Classe dos dados da resposta.
 */
public class ApiResponse<T> {
    /**
     * Status da resposta da chamada.
     */
    private final int status;

    /**
     * Dados de resposta.
     */
    private final T data;

    /**
     * Construtor com argumentos
     *
     * @param status Código de Status da resposta da chamada.
     * @param data   Dados de resposta.
     */
    public ApiResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    /**
     * Retorna o Status da resposta da chamada.
     *
     * @return O Status da resposta da chamada.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Retorna os Dados de resposta.
     *
     * @return Os Dados de resposta.
     */
    public T getData() {
        return data;
    }
}
