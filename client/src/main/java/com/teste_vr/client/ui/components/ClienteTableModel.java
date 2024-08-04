package com.teste_vr.client.ui.components;

import com.teste_vr.client.dtos.clientes.ClienteDTO;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Uma {@link AbstractTableModel} para {@link ClienteDTO}.
 */
public class ClienteTableModel extends AbstractTableModel {
    /**
     * Lista de {@link ClienteDTO} para ser mostrada.
     */
    private final List<ClienteDTO> clientes;

    /**
     * Coluna das Tabelas.
     */
    private final String[] colunas = {"Código", "Nome", "Limite de Compra", "Data de Fechamento"};

    /**
     * Constutor padrão.
     *
     * @param clientes Lista de Clientes cadastrados.
     */
    public ClienteTableModel(List<ClienteDTO> clientes) {
        this.clientes = clientes;
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClienteDTO cliente = clientes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cliente.getCodigo();
            case 1:
                return cliente.getNome();
            case 2:
                return cliente.getLimiteCompra();
            case 3:
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                return sdf.format(cliente.getDataFechamentoFatura());
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    /**
     * Retorna o Cliente a partir do indice da linha.
     *
     * @param rowIndex Indice da Linha
     * @return o {@link ClienteDTO}
     */
    public ClienteDTO getClienteAt(int rowIndex) {
        return clientes.get(rowIndex);
    }

    /**
     * Remove o Cliente a partir do indice da linha.
     *
     * @param rowIndex Indice da Linha.
     */
    public void removeClienteAt(int rowIndex) {
        clientes.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    /**
     * Adiciona um Cliente na Tabela.
     *
     * @param cliente um {@link ClienteDTO}.
     */
    public void addCliente(ClienteDTO cliente) {
        clientes.add(cliente);
        fireTableRowsInserted(clientes.size() - 1, clientes.size() - 1);
    }
}