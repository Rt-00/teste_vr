package com.teste_vr.client.ui.components;

import com.teste_vr.client.dtos.itempedido.ItemPedidoDTO;
import com.teste_vr.client.ui.pedido.PedidoCadastroFrame;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoTableModel extends AbstractTableModel {
    private List<ItemPedidoDTO> itensPedidos;
    private String[] colunas = {"Código", "Descrição", "Quantidade", "Preço"};
    private PedidoCadastroFrame parentFrame;

    public ItemPedidoTableModel(List<ItemPedidoDTO> itensPedidos, PedidoCadastroFrame parentFrame) {
        this.itensPedidos = itensPedidos != null ? itensPedidos : new ArrayList<>();
        this.parentFrame = parentFrame;
    }

    public void adicionarProdutoPedido(ItemPedidoDTO itemPedido) {
        for (int i = 0; i < itensPedidos.size(); i++) {
            ItemPedidoDTO itemExistente = itensPedidos.get(i);
            if (itemExistente.getProduto().getCodigo().equals(itemPedido.getProduto().getCodigo())) {
                itemExistente.setQuantidade(itemExistente.getQuantidade() + itemPedido.getQuantidade());
                itemExistente.setPreco(itemExistente.getPreco().add(itemPedido.getPreco()));
                fireTableRowsUpdated(i, i); // Atualiza a linha específica
                return;
            }
        }
        itensPedidos.add(itemPedido);
        int rowIndex = itensPedidos.size() - 1;
        fireTableRowsInserted(rowIndex, rowIndex); // Adiciona a nova linha
    }

    public void removerProdutoPedido(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < itensPedidos.size()) {
            itensPedidos.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex); // Remove a linha específica
        }
    }

    public List<ItemPedidoDTO> getItensPedidos() {
        return itensPedidos;
    }

    @Override
    public int getRowCount() {
        return itensPedidos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    private void atualizarValorTotal() {
        if (parentFrame != null) {
            parentFrame.atualizarValorTotal();
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        ItemPedidoDTO itemPedido = itensPedidos.get(rowIndex);
        switch (columnIndex) {
            case 2: // Quantidade
                try {
                    int quantidade = Integer.parseInt(value.toString());
                    itemPedido.setQuantidade(quantidade);
                    atualizarValorTotal();
                    fireTableCellUpdated(rowIndex, columnIndex);
                    fireTableDataChanged();
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
                break;
            default:
                break;
        }
    }

    public BigDecimal calcularValorTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedidoDTO item : itensPedidos) {
            total = total.add(item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }
        return total;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemPedidoDTO itemPedido = itensPedidos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return itemPedido.getProduto().getCodigo();
            case 1:
                return itemPedido.getProduto().getDescricao();
            case 2:
                return itemPedido.getQuantidade();
            case 3:
                return itemPedido.getPreco();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Apenas a coluna de Quantidade pode ser editada
        return columnIndex == 2;
    }
}
