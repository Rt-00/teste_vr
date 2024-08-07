package com.teste_vr.server.services.pedido;

import com.teste_vr.server.dtos.cliente.ListClienteDTO;
import com.teste_vr.server.dtos.pedido.CreatePedidoDTO;
import com.teste_vr.server.dtos.pedido.UpdatePedidoDTO;
import com.teste_vr.server.repositories.PedidoRepository;
import com.teste_vr.server.services.cliente.ClienteService;
import com.teste_vr.server.services.produto.ProdutoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PedidoValidationService {

    private final ClienteService clienteService;
    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;

    public PedidoValidationService(ClienteService clienteService,
                                   PedidoRepository pedidoRepository,
                                   ProdutoService produtoService
    ) {
        this.clienteService = clienteService;
        this.pedidoRepository = pedidoRepository;
        this.produtoService = produtoService;
    }

    public void validarCreatePedidoDTO(CreatePedidoDTO createPedidoDTO) {
        if (createPedidoDTO.itensPedido().isEmpty()) {
            throw new IllegalArgumentException("O pedido precisa ter ao menos um Item.");
        }

        if (createPedidoDTO.cliente() == null) {
            throw new IllegalArgumentException("O pedido precisa ter um Cliente.");
        }

        if (createPedidoDTO.itensPedido().stream().anyMatch(cip -> cip.quantidade().compareTo(0) <= 0)) {
            throw new IllegalArgumentException("A quantidade deve ser maior ou igual a zero.");
        }

        if (createPedidoDTO.itensPedido().stream().anyMatch(cip -> cip.codigo().compareTo(0L) <= 0)) {
            throw new IllegalArgumentException("O codigo do Item do Pedido deve ser maior ou igual a zero.");
        }

        if (createPedidoDTO.itensPedido().stream().anyMatch(cip -> cip.preco().compareTo(BigDecimal.ZERO) <= 0)) {
            throw new IllegalArgumentException("O preço do Item do Pedido deve ser maior ou igual a zero.");
        }

        if (createPedidoDTO.valorTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor do pedido deve ser maior ou igual a zero.");
        }

        ListClienteDTO clienteExistente = clienteService.obterClientePorCodigo(createPedidoDTO.cliente().codigo());

        BigDecimal totalComprasAposFechamento = pedidoRepository.findTotalComprasAposFechamento(
                clienteExistente.codigo(),
                clienteExistente.dataFechamentoFatura());

        if (totalComprasAposFechamento == null) {
           totalComprasAposFechamento = BigDecimal.ZERO;
        }

        BigDecimal limiteDisponivel = clienteExistente.limiteCompra().subtract(totalComprasAposFechamento);

        if (createPedidoDTO.valorTotal().compareTo(limiteDisponivel) > 0) {
            throw new IllegalArgumentException(String.format(
                    "Limite de crédito excedido. Limite disponível: R$ %.2f, Data de fechamento: %s",
                    limiteDisponivel, createPedidoDTO.cliente().dataFechamentoFatura()));
        }

        createPedidoDTO.itensPedido().stream().map(createItemPedidoDTO -> {
            // Apenas para disparar a validação de produto não encontrado.
            return produtoService.obterProdutoPorCodigo(createItemPedidoDTO.produto().codigo());
        });

    }

    public void validarUpdatePedidoDTO(UpdatePedidoDTO updatePedidoDTO, Long codigo) {
        if (updatePedidoDTO.itensPedido().isEmpty()) {
            throw new IllegalArgumentException("O pedido precisa ter ao menos um Item.");
        }

        if (updatePedidoDTO.cliente() == null) {
            throw new IllegalArgumentException("O pedido precisa ter um Cliente.");
        }

        if (updatePedidoDTO.itensPedido().stream().anyMatch(cip -> cip.quantidade().compareTo(0) <= 0)) {
            throw new IllegalArgumentException("A quantidade deve ser maior ou igual a zero.");
        }

        if (updatePedidoDTO.itensPedido().stream().anyMatch(cip -> cip.codigo().compareTo(0L) <= 0)) {
            throw new IllegalArgumentException("O codigo do Item do Pedido deve ser maior ou igual a zero.");
        }

        if (updatePedidoDTO.itensPedido().stream().anyMatch(cip -> cip.preco().compareTo(BigDecimal.ZERO) <= 0)) {
            throw new IllegalArgumentException("O preço do Item do Pedido deve ser maior ou igual a zero.");
        }

        if (updatePedidoDTO.valorTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor do pedido deve ser maior ou igual a zero.");
        }

        ListClienteDTO clienteExistente = clienteService.obterClientePorCodigo(updatePedidoDTO.cliente().codigo());

        BigDecimal totalComprasAposFechamento = pedidoRepository.findTotalComprasAposFechamento(
                clienteExistente.codigo(),
                clienteExistente.dataFechamentoFatura());

        if (totalComprasAposFechamento == null) {
            totalComprasAposFechamento = BigDecimal.ZERO;
        }

        BigDecimal limiteDisponivel = clienteExistente.limiteCompra().subtract(totalComprasAposFechamento);

        if (updatePedidoDTO.valorTotal().compareTo(limiteDisponivel) > 0) {
            throw new IllegalArgumentException(String.format(
                    "Limite de crédito excedido. Limite disponível: R$ %.2f, Data de fechamento: %s",
                    limiteDisponivel, updatePedidoDTO.cliente().dataFechamentoFatura()));
        }

        updatePedidoDTO.itensPedido().stream().map(createItemPedidoDTO -> {
            // Apenas para disparar a validação de produto não encontrado.
            return produtoService.obterProdutoPorCodigo(createItemPedidoDTO.produto().codigo());
        });
    }
}
