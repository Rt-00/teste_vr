package com.teste_vr.client.dtos.exception;

/**
 * DTO para representação das Exceções retornadas do servidor.
 *
 * @param message    Mensagem retornada do Servidor
 * @param statusCode Status Code da Requisição.
 */
public record ExceptionDTO(String message, String statusCode) {
}
