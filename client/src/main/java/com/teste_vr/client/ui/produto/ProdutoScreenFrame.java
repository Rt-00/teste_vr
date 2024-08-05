package com.teste_vr.client.ui.produto;

import com.teste_vr.client.dtos.produto.ProdutoDTO;
import com.teste_vr.client.services.produto.ProdutoService;
import com.teste_vr.client.ui.components.ProdutoTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Frame responsável pelo CRUD de Produto.
 */
public class ProdutoScreenFrame extends JFrame {
    /**
     * Serviço de Produtos.
     */
    private ProdutoService produtoService;

    /**
     * {@link javax.swing.table.AbstractTableModel} de Produto.
     */
    private final ProdutoTableModel tableModel;

    /**
     * Tabela de Produtos.
     */
    private final JTable produtosTable;

    /**
     * Botão para Criação.
     */
    private final JButton btnCriar;

    /**
     * Botão para Exclusão.
     */
    private final JButton btnExcluir;

    /**
     * Botão para Atualização.
     */
    private final JButton btnAtualizar;

    /**
     * Construtor Padrão
     *
     * @param produtos Produtos cadastrados.
     */
    public ProdutoScreenFrame(List<ProdutoDTO> produtos) {
        produtoService = new ProdutoService();

        setTitle("Gerenciar Produtos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 400);
        setLayout(new BorderLayout());

        tableModel = new ProdutoTableModel(produtos);
        produtosTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(produtosTable);
        add(scrollPane, BorderLayout.CENTER);

        // Painel para os botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Botão Criar
        btnCriar = new JButton("Criar Produto");
        buttonPanel.add(btnCriar);

        // Botão Excluir
        btnExcluir = new JButton("Excluir Produto");
        buttonPanel.add(btnExcluir);

        // Botão Atualizar
        btnAtualizar = new JButton("Atualizar Produto");
        buttonPanel.add(btnAtualizar);

        add(buttonPanel, BorderLayout.SOUTH);

        // Configurar ações dos botões
        btnCriar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProdutoCadastroFrame(null);
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCliente();
            }
        });

        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCliente();
            }
        });
    }

    /**
     * Abre o {@link ProdutoCadastroFrame}.
     *
     * @param produto Produto que será atualizado.
     */
    private void openProdutoCadastroFrame(ProdutoDTO produto) {
        ProdutoCadastroFrame cadastroFrame = new ProdutoCadastroFrame(produto, newProduto -> {
            if (produto == null) {
                tableModel.addProduto(newProduto);
            } else {
                tableModel.fireTableDataChanged();
            }
        });

        cadastroFrame.setVisible(true);
    }

    /**
     * Abre o {@link ProdutoCadastroFrame} passando o Produto selecionado para ser atualizado.
     */
    private void updateCliente() {
        int selectedRow = produtosTable.getSelectedRow();
        if (selectedRow >= 0) {
            ProdutoDTO produto = tableModel.getProdutoAt(selectedRow);
            openProdutoCadastroFrame(produto);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para atualizar.",
                    "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Método para excluir o Produto selecionado.
     */
    private void deleteCliente() {
        int selectedRow = produtosTable.getSelectedRow();
        if (selectedRow >= 0) {
            ProdutoDTO produto = tableModel.getProdutoAt(selectedRow);
            int input = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir esse produto?", "Confirmação",
                    JOptionPane.YES_NO_OPTION);
            if (input != 0) {
                return;
            }

            try {
                produtoService.excluirProduto(produto.getCodigo());
                tableModel.removeProdutoAt(selectedRow);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir produto.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.",
                    "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
