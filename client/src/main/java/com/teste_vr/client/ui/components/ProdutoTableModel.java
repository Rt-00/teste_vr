package com.teste_vr.client.ui.components;

import com.teste_vr.client.dtos.produto.ProdutoDTO;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Uma {@link AbstractTableModel} para {@link ProdutoDTO}.
 */
public class ProdutoTableModel extends AbstractTableModel {
    /**
     * Lista de {@link ProdutoDTO} para ser mostrada.
     */
    private List<ProdutoDTO> produtos;

    /**
     * Colunas da Tabela.
     */
    private final String[] colunas = {"Código", "Descrição", "Preço"};

    /**
     * Constutor Padrão.
     *
     * @param produtos Lista de Produtos cadastrados.
     */
    public ProdutoTableModel(List<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }

    @Override
    public int getRowCount() {
        return produtos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProdutoDTO produto = produtos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return produto.getCodigo();
            case 1:
                return produto.getDescricao();
            case 2:
                return produto.getPreco();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    /**
     * Adiciona um Produto na Tabela.
     *
     * @param produto Um {@link ProdutoDTO}.
     */
    public void addProduto(ProdutoDTO produto) {
        produtos.add(produto);
        fireTableRowsInserted(produtos.size() - 1, produtos.size() - 1);
    }

    /**
     * Remove o Produto a partir do indice da lina.
     *
     * @param rowIndex Indice da Linha.
     */
    public void removeProdutoAt(int rowIndex) {
        produtos.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    /**
     * Retorna o Produto a partir do indice da linha.
     *
     * @param rowIndex Indice da Linha
     * @return O {@link ProdutoDTO}
     */
    public ProdutoDTO getProdutoAt(int rowIndex) {
        return produtos.get(rowIndex);
    }
}
