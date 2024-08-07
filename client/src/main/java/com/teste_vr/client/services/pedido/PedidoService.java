package com.teste_vr.client.services.pedido;

import com.teste_vr.client.api.PedidoApiClient;
import com.teste_vr.client.dtos.api.ApiResponse;
import com.teste_vr.client.dtos.clientes.ClienteDTO;
import com.teste_vr.client.dtos.pedidos.PedidoDTO;
import com.teste_vr.client.dtos.produto.ProdutoDTO;

import java.util.Date;
import java.util.List;

public class PedidoService {

    private final PedidoApiClient pedidoApiClient;

    public PedidoService() {
        this.pedidoApiClient = new PedidoApiClient();
    }

    public ApiResponse<List<PedidoDTO>> getPedidosCadastrados() throws Exception {
        return pedidoApiClient.buscarPedidos();
    }

    public void excluirPedido(Long codigo) throws Exception {
        pedidoApiClient.excluirPedido(codigo);
    }

    public void salvarPedido(PedidoDTO pedidoDTO) throws Exception {
        pedidoApiClient.salvarPedido(pedidoDTO);
    }

    public void atualizarPedido(PedidoDTO pedidoDTO) throws Exception {
        pedidoApiClient.atualizarPedido(pedidoDTO);
    }

    public ApiResponse<List<PedidoDTO>> filtrarPedidos(Date dataDoPedido, Long clienteCodigo, Long produtoCodigo)
            throws Exception {
        return pedidoApiClient.filtrarPedidos(dataDoPedido, clienteCodigo, produtoCodigo);
    }
}
