package com.teste_vr.client.ui.produto;

import com.teste_vr.client.dtos.api.ApiResponse;
import com.teste_vr.client.dtos.produto.ProdutoDTO;
import com.teste_vr.client.services.produto.ProdutoService;
import com.teste_vr.client.ui.components.ProdutoTableModel;
import com.teste_vr.client.ui.pedido.PedidoCadastroFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProdutoSelecionarFrame extends JFrame {
    private JTable tabelaProdutos;
    private ProdutoTableModel tableModelProdutos;
    private ProdutoService produtoService;
    private PedidoCadastroFrame pedidoCadastroFrame;

    public ProdutoSelecionarFrame(PedidoCadastroFrame pedidoCadastroFrame) {
        this.pedidoCadastroFrame = pedidoCadastroFrame;
        this.produtoService = new ProdutoService();

        setTitle("Selecionar Produto");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabela de Produtos
        ApiResponse<List<ProdutoDTO>> apiResponse = null;
        try {
            apiResponse = produtoService.getProdutosCadastrados();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter Produtos da API. Status: " +
                    apiResponse.getStatus(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        tableModelProdutos = new ProdutoTableModel(apiResponse.getData());
        tabelaProdutos = new JTable(tableModelProdutos);
        JScrollPane scrollPaneProdutos = new JScrollPane(tabelaProdutos);
        add(scrollPaneProdutos, BorderLayout.CENTER);

        // Botões
        JPanel panelBotoes = new JPanel();
        JButton btnSelecionar = new JButton("Selecionar");
        JButton btnCancelar = new JButton("Cancelar");

        panelBotoes.add(btnSelecionar);
        panelBotoes.add(btnCancelar);

        add(panelBotoes, BorderLayout.SOUTH);

        // Eventos dos Botões
        btnSelecionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selecionarProduto();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void selecionarProduto() {
        int linhaSelecionada = tabelaProdutos.getSelectedRow();
        if (linhaSelecionada >= 0) {
            ProdutoDTO produtoSelecionado = tableModelProdutos.getProdutoAt(linhaSelecionada);
            if (produtoSelecionado != null) {
                pedidoCadastroFrame.adicionarProduto(produtoSelecionado);
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto.");
        }
    }
}