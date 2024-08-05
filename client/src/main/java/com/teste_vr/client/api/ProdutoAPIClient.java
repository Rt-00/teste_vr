package com.teste_vr.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste_vr.client.dtos.api.ApiResponse;
import com.teste_vr.client.dtos.exception.ExceptionDTO;
import com.teste_vr.client.dtos.produto.ProdutoDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * Classe que representa um Client de API para o endpoint de Produtos.
 */
public class ProdutoAPIClient {

    /**
     * {@link HttpClient} para as chamadas ao servidor.
     */
    private final HttpClient httpClient;

    /**
     * Um {@link ObjectMapper} para fazer a conversão entre Objeto Java e JSON.
     */
    private final ObjectMapper objectMapper;

    /**
     * URL base do endpoint;
     */
    private static final String API_BASE_URL = "http://localhost:8080/produtos";

    /**
     * Construtor sem argumentos.
     */
    public ProdutoAPIClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Faz a requisição para listar todos os produtos da base de dados.
     *
     * @return uma {@link ApiResponse} com uma Lista de {@link ProdutoDTO}.
     * @throws IOException caso a resposta seja diferente de 200.
     * @throws Exception   outra exceção qualquer.
     */
    public ApiResponse<List<ProdutoDTO>> buscarProdutos() throws IOException, Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        List<ProdutoDTO> produtos = objectMapper.readValue(response.body(), new TypeReference<List<ProdutoDTO>>() {
        });

        if (response.statusCode() != 200) {
            ExceptionDTO exception = objectMapper.readValue(response.body(), ExceptionDTO.class);
            throw new IOException(exception.message());
        }

        return new ApiResponse<>(response.statusCode(), produtos);
    }

    /**
     * Faz a requisição para excluir um Produto da Base de Dados.
     *
     * @param codigo O Código do produto que será exlcuido.
     * @throws IOException caso a resposta seja diferente de 204.
     * @throws Exception   outra exceção qualquer.
     */
    public void excluirProduto(Long codigo) throws IOException, Exception {
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
     * Faz a requisição para salvar um novo Produto na Base de dados.
     *
     * @param produtoDTO O {@link ProdutoDTO} que será criado.
     * @throws IOException caso a resposta seja diferente de 201.
     * @throws Exception   outra exceção qualquer.
     */
    public void salvarProduto(ProdutoDTO produtoDTO) throws IOException, Exception {
        String jsonBody = objectMapper.writeValueAsString(produtoDTO);

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
     * Faz a requisição para atualizar um Produto da base de dados.
     *
     * @return uma {@link ApiResponse} com um {@link ProdutoDTO} atualizado.
     * @throws IOException caso a resposta seja diferente de 200.
     * @throws Exception   outra exceção qualquer.
     */
    public ApiResponse<ProdutoDTO> atualizarProduto(ProdutoDTO produtoDTO) throws IOException,
            Exception {
        String jsonBody = objectMapper.writeValueAsString(produtoDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/" + produtoDTO.getCodigo()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            ExceptionDTO exception = objectMapper.readValue(response.body(), ExceptionDTO.class);
            throw new IOException(exception.message());
        }

        ProdutoDTO produto = objectMapper.readValue(response.body(), ProdutoDTO.class);

        return new ApiResponse(response.statusCode(), produto);
    }
}
