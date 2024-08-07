package com.teste_vr.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste_vr.client.dtos.api.ApiResponse;
import com.teste_vr.client.dtos.exception.ExceptionDTO;
import com.teste_vr.client.dtos.pedidos.PedidoDTO;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Classe que representa um Client de API para o endpoint de Pedidos.
 */
public class PedidoApiClient {
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
    private static final String API_BASE_URL = "http://localhost:8080/pedidos";

    /**
     * Construtor sem argumentos.
     */
    public PedidoApiClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public ApiResponse<List<PedidoDTO>> buscarPedidos() throws IOException, Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        List<PedidoDTO> pedidos = objectMapper.readValue(response.body(), new TypeReference<List<PedidoDTO>>() {
        });

        if (response.statusCode() != 200) {
            ExceptionDTO exception = objectMapper.readValue(response.body(), ExceptionDTO.class);
            throw new IOException(exception.message());
        }

        return new ApiResponse<>(response.statusCode(), pedidos);
    }

    public void excluirPedido(Long codigo) throws IOException, Exception {
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

    public void salvarPedido(PedidoDTO pedidoDTO) throws IOException, Exception {
        String jsonBody = objectMapper.writeValueAsString(pedidoDTO);

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

    public ApiResponse<PedidoDTO> atualizarPedido(PedidoDTO pedidoDTO) throws IOException, Exception {
        String jsonBody = objectMapper.writeValueAsString(pedidoDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/" + pedidoDTO.getCodigo()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            ExceptionDTO exception = objectMapper.readValue(response.body(), ExceptionDTO.class);
            throw new IOException(exception.message());
        }

        PedidoDTO pedido = objectMapper.readValue(response.body(), PedidoDTO.class);

        return new ApiResponse(response.statusCode(), pedido);
    }

    public ApiResponse<List<PedidoDTO>> filtrarPedidos(Date dataDoPedido, Long clienteCodigo, Long produtoCodigo)
            throws IOException, Exception {
        class FiltrarPedidoDTO {
            private Date dataPedido;
            private Long clienteCodigo;
            private Long produtoCodigo;

            public FiltrarPedidoDTO() {
            }

            public FiltrarPedidoDTO(Date dataPedido, Long clenteCodigo, Long produtoCodigo) {
                this.dataPedido = dataPedido;
                this.clienteCodigo = clenteCodigo;
                this.produtoCodigo = produtoCodigo;
            }

            public Date getDataPedido() {
                return dataPedido;
            }

            public void setDataPedido(Date dataPedido) {
                this.dataPedido = dataPedido;
            }

            public Long getClienteCodigo() {
                return clienteCodigo;
            }

            public void setClienteCodigo(Long clienteCodigo) {
                this.clienteCodigo = clienteCodigo;
            }

            public Long getProdutoCodigo() {
                return produtoCodigo;
            }

            public void setProdutoCodigo(Long produtoCodigo) {
                this.produtoCodigo = produtoCodigo;
            }
        }

        FiltrarPedidoDTO filtrarPedidoDTO = new FiltrarPedidoDTO(dataDoPedido, clienteCodigo, produtoCodigo);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormatted = sdf.format(dataDoPedido);

        String query = String.format(
                "clienteCodigo=%s&produtoCodigo=%s&dataHora=%s",
                URLEncoder.encode(clienteCodigo.toString(), StandardCharsets.UTF_8.name()),
                URLEncoder.encode(produtoCodigo.toString(), StandardCharsets.UTF_8.name()),
                URLEncoder.encode(dateFormatted, StandardCharsets.UTF_8.name())
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/"
                        + "filtrar?"
                        + query))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            ExceptionDTO exception = objectMapper.readValue(response.body(), ExceptionDTO.class);
            throw new IOException(exception.message());
        }

        List<PedidoDTO> pedidos = objectMapper.readValue(response.body(), new TypeReference<List<PedidoDTO>>() {
        });

        return new ApiResponse(response.statusCode(), pedidos);
    }
}
