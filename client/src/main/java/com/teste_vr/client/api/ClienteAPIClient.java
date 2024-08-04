package com.teste_vr.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste_vr.client.dtos.ExceptionDTO;
import com.teste_vr.client.dtos.api.ApiResponse;
import com.teste_vr.client.dtos.clientes.ClienteDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


/**
 * Classe que representa um Client de API para o endpoint de Clientes.
 */
public class ClienteAPIClient {

    /**
     * URL base do endpoint;
     */
    private static final String API_BASE_URL = "http://localhost:8080/clientes";

    /**
     * {@link HttpClient} para as chamadas ao servidor.
     */
    private final HttpClient httpClient;

    /**
     * Um {@link ObjectMapper} para fazer a conversão entre Objeto Java e JSON.
     */
    private final ObjectMapper objectMapper;

    /**
     * Construtor sem argumentos.
     */
    public ClienteAPIClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Faz a requisição para salvar o cliente na base de dados.
     *
     * @param clienteDTO Um {@link ClienteDTO}.
     * @throws IOException caso a resposta seja diferente de 201.
     * @throws Exception   outra exceção qualquer.
     */
    public void salvarCliente(ClienteDTO clienteDTO) throws IOException, InterruptedException {
        String jsonBody = objectMapper.writeValueAsString(clienteDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            ExceptionDTO exception = objectMapper.readValue(response.body(), ExceptionDTO.class);
            throw new IOException(exception.message());
        }
    }

    /**
     * Faz a requisição para listar todos os clientes da base de dados.
     *
     * @return uma {@link ApiResponse} com uma Lista de {@link ClienteDTO}.
     * @throws IOException caso a resposta seja diferente de 200.
     * @throws Exception   outra exceção qualquer.
     */
    public ApiResponse<List<ClienteDTO>> buscarClientes() throws IOException, Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        List<ClienteDTO> clientes = objectMapper.readValue(response.body(), new TypeReference<List<ClienteDTO>>() {
        });

        if (response.statusCode() != 200) {
            ExceptionDTO exception = objectMapper.readValue(response.body(), ExceptionDTO.class);
            throw new IOException(exception.message());
        }

        return new ApiResponse<>(response.statusCode(), clientes);
    }

    /**
     * Faz a requisição para excluir um Cliente da Base de Dados.
     *
     * @param codigo O Código do cliente que será exlcuido.
     * @throws IOException caso a resposta seja diferente de 204.
     * @throws Exception   outra exceção qualquer.
     */
    public void excluirCliente(Long codigo) throws IOException, Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/" + codigo))
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            ExceptionDTO exception = objectMapper.readValue(response.body(), ExceptionDTO.class);
            throw new IOException(exception.message());
        }
    }

    /**
     * Faz a requisição para atualizar um cliente da base de dados.
     *
     * @return uma {@link ApiResponse} com um {@link ClienteDTO} atualizado.
     * @throws IOException caso a resposta seja diferente de 200.
     * @throws Exception   outra exceção qualquer.
     */
    public ApiResponse<ClienteDTO> atualizarCliente(ClienteDTO clienteDTO) throws IOException,
            Exception {
        String jsonBody = objectMapper.writeValueAsString(clienteDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/" + clienteDTO.getCodigo()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            ExceptionDTO exception = objectMapper.readValue(response.body(), ExceptionDTO.class);
            throw new IOException(exception.message());
        }

        ClienteDTO cliente = objectMapper.readValue(response.body(), ClienteDTO.class);

        return new ApiResponse(response.statusCode(), cliente);
    }
}
