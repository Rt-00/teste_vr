package com.teste_vr.client.ui.components;

import com.teste_vr.client.dtos.pedidos.PedidoDTO;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class PedidoTableModel extends AbstractTableModel {
    private final String[] colunas = {"Código", "Cliente", "Valor Total", "Data do Pedido"};
    private List<PedidoDTO> pedidos;

    public PedidoTableModel(List<PedidoDTO> pedidos) {
        this.pedidos = pedidos;
    }

    public void addPedido(PedidoDTO pedido) {
        pedidos.add(pedido);
        fireTableRowsInserted(pedidos.size() - 1, pedidos.size() - 1);
    }

    @Override
    public int getRowCount() {
        return pedidos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PedidoDTO pedido = pedidos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return pedido.getCodigo();
            case 1:
                return pedido.getCliente().getNome(); // Supondo que ClienteDTO tem o método getNome()
            case 2:
                return pedido.getValorTotal();
            case 3:
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                return sdf.format(pedido.getDataHora());
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public PedidoDTO getPedidoAt(int rowIndex) {
        return pedidos.get(rowIndex);
    }

    public void removePedidoAt(int rowIndex) {
        pedidos.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}
