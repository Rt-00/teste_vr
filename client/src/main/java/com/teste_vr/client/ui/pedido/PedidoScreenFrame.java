package com.teste_vr.client.ui.pedido;

import com.teste_vr.client.dtos.api.ApiResponse;
import com.teste_vr.client.dtos.clientes.ClienteDTO;
import com.teste_vr.client.dtos.pedidos.PedidoDTO;
import com.teste_vr.client.dtos.produto.ProdutoDTO;
import com.teste_vr.client.services.cliente.ClienteService;
import com.teste_vr.client.services.pedido.PedidoService;
import com.teste_vr.client.services.produto.ProdutoService;
import com.teste_vr.client.ui.components.PedidoTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PedidoScreenFrame extends JFrame {

    private PedidoService pedidoService;

    private final PedidoTableModel tableModel;

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
    private JComboBox<ClienteDTO> clienteComboBox;
    private JComboBox<ProdutoDTO> produtoComboBox;

    private final ClienteService clienteService;
    private final ProdutoService produtoService;


    JTable tabelaPedidos;

    public PedidoScreenFrame(List<PedidoDTO> pedidoDTOS) {
        pedidoService = new PedidoService();
        produtoService = new ProdutoService();
        clienteService = new ClienteService();

        setTitle("Gerenciar Pedidos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 400);
        setLayout(new BorderLayout());

        clienteComboBox = new JComboBox<>();
        setClientes();

        produtoComboBox = new JComboBox<>();
        setProdutos();

        // Configuração da tabela
        tableModel = new PedidoTableModel(pedidoDTOS); // Supondo que o serviço tem esse método
        tabelaPedidos = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tabelaPedidos);
        add(scrollPane, BorderLayout.CENTER);

        // Painel para os botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Botão Criar
        btnCriar = new JButton("Criar Pedido");
        buttonPanel.add(btnCriar);

        // Botão Excluir
        btnExcluir = new JButton("Excluir Pedido");
        buttonPanel.add(btnExcluir);

        // Botão Atualizar
        btnAtualizar = new JButton("Atualizar Pedido");
        buttonPanel.add(btnAtualizar);

        add(buttonPanel, BorderLayout.SOUTH);

        // Configurar ações dos botões
        btnCriar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPedidoCadastroFrame(null);
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePedido();
            }
        });

        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePedido();
            }
        });
    }

    private void openPedidoCadastroFrame(PedidoDTO pedidoDTO) {
        PedidoCadastroFrame cadastroFrame = new PedidoCadastroFrame(pedidoDTO, newPedido -> {
            if (pedidoDTO == null) {
                tableModel.addPedido(newPedido);
            } else {
                tableModel.fireTableDataChanged();
            }
        });

        cadastroFrame.setVisible(true);
    }

    private void deletePedido() {
        int selectedRow = tabelaPedidos.getSelectedRow();
        if (selectedRow >= 0) {
            PedidoDTO pedido = tableModel.getPedidoAt(selectedRow);
            int input = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir esse pedido?", "Confirmação",
                    JOptionPane.YES_NO_OPTION);
            if (input != 0) {
                return;
            }

            try {
                pedidoService.excluirPedido(pedido.getCodigo());
                tableModel.removePedidoAt(selectedRow);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir pedido.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um pedido para excluir.",
                    "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updatePedido() {
        int selectedRow = tabelaPedidos.getSelectedRow();
        if (selectedRow >= 0) {
            PedidoDTO pedido = tableModel.getPedidoAt(selectedRow);
            openPedidoCadastroFrame(pedido);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para atualizar.",
                    "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void setClientes() {
        ApiResponse<List<ClienteDTO>> apiResponse = null;
        try {
            apiResponse = clienteService.getClienteCadastrados();

            clienteComboBox.addItem(null);
            for (ClienteDTO cliente : apiResponse.getData()) {
                clienteComboBox.addItem(cliente);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter Produtos da API. Status: " +
                    apiResponse.getStatus(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setProdutos() {
        ApiResponse<List<ProdutoDTO>> apiResponse = null;
        try {
            apiResponse = produtoService.getProdutosCadastrados();
            produtoComboBox.addItem(null);

            for (ProdutoDTO produto : apiResponse.getData()) {
                produtoComboBox.addItem(produto);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro de comunicação com o servidor:",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}


