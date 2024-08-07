package com.teste_vr.client.dtos.pedidos;

import com.teste_vr.client.dtos.clientes.ClienteDTO;
import com.teste_vr.client.dtos.itempedido.ItemPedidoDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PedidoDTO {

    private Long codigo;

    private ClienteDTO cliente;

    private List<ItemPedidoDTO> itensPedido;

    private BigDecimal valorTotal;

    private Date dataHora;

    public PedidoDTO() {
    }

    public PedidoDTO(Long codigo, ClienteDTO cliente, List<ItemPedidoDTO> itensPedido, BigDecimal valorTotal,
                     Date dataHora) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.itensPedido = itensPedido;
        this.valorTotal = valorTotal;
        this.dataHora = dataHora;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedidoDTO> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItemPedidoDTO> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }


    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }
}
