package com.teste_vr.client.services.cliente;

import com.teste_vr.client.api.ClienteAPIClient;
import com.teste_vr.client.dtos.api.ApiResponse;
import com.teste_vr.client.dtos.clientes.ClienteDTO;

import java.util.List;

/**
 * Serviço de Cliente.
 */
public class ClienteService {
    private final ClienteAPIClient clienteApiClient;

    /**
     * Construtor padrão.
     */
    public ClienteService() {
        this.clienteApiClient = new ClienteAPIClient();
    }

    /**
     * Utiliza o {@link ClienteAPIClient} para a criação de Cliente.
     *
     * @param clienteDTO {@link ClienteDTO} a ser criado.
     * @throws Exception exceção qualquer.
     */
    public void salvarCliente(ClienteDTO clienteDTO) throws Exception {
        clienteApiClient.salvarCliente(clienteDTO);
    }

    /**
     * Utiliza o {@link ClienteAPIClient} para obter os Clientes cadastrados.
     *
     * @return {@link ApiResponse} contendo uma {@link List} de {@link ClienteDTO} cadastrados.
     * @throws Exception uma exceção qualquer.
     */
    public ApiResponse<List<ClienteDTO>> getClienteCadastrados() throws Exception {
        return clienteApiClient.buscarClientes();
    }

    /**
     * Utiliza o {@link ClienteAPIClient} para a exclusão de Cliente.
     *
     * @param codigo O Código do cliente a ser excluido.
     * @throws Exception exceção qualquer.
     */
    public void excluirCliente(Long codigo) throws Exception {
        clienteApiClient.excluirCliente(codigo);
    }

    /**
     * Utiliza o {@link ClienteAPIClient} para a atualização de Cliente.
     *
     * @param clienteDTO o {@link ClienteDTO} do Cliente que será atualizado.
     * @throws Exception outra exceção qualquer.
     */
    public void atualizarCliente(ClienteDTO clienteDTO) throws Exception {
        clienteApiClient.atualizarCliente(clienteDTO);
    }
}
