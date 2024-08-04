package com.teste_vr.client.ui.cliente;

import com.teste_vr.client.dtos.api.ApiResponse;
import com.teste_vr.client.dtos.clientes.ClienteDTO;
import com.teste_vr.client.services.cliente.ClienteService;
import com.teste_vr.client.ui.components.ClienteTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Frame responsável pelo CRUD de Cliente.
 */
public class ClienteScreenFrame extends JFrame {
    /**
     * Tabela de Clientes.
     */
    private final JTable clienteTable;

    /**
     * Botão para Criação.
     */
    private final JButton btnCriar;

    /**
     * Botão para Exclusão.
     */
    private final JButton btnExcluir;

    /**
     * Botão para atualização.
     */
    private final JButton btnAtualizar;

    /**
     * {@link javax.swing.table.AbstractTableModel} de Cliente.
     */
    ClienteTableModel tableModel;

    /**
     * Service de Cliente.
     */
    private final ClienteService clienteService;

    /**
     * Construtor padrão.
     *
     * @param clientes Clientes cadastrados.
     */
    public ClienteScreenFrame(List<ClienteDTO> clientes) {
        clienteService = new ClienteService();

        setTitle("Gerenciar Clientes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tableModel = new ClienteTableModel(clientes);
        clienteTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(clienteTable);
        add(scrollPane, BorderLayout.CENTER);

        // Painel para os botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Botão Criar
        btnCriar = new JButton("Criar Cliente");
        buttonPanel.add(btnCriar);

        // Botão Excluir
        btnExcluir = new JButton("Excluir Cliente");
        buttonPanel.add(btnExcluir);

        // Botão Atualizar
        btnAtualizar = new JButton("Atualizar Cliente");
        buttonPanel.add(btnAtualizar);

        add(buttonPanel, BorderLayout.SOUTH);

        // Configurar ações dos botões
        btnCriar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openClienteCadastroFrame(null);
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
     * Abre o {@link ClienteCadastroFrame}.
     *
     * @param cliente Cliente que será atualizado.
     */
    private void openClienteCadastroFrame(ClienteDTO cliente) {
        ClienteCadastroFrame cadastroFrame = new ClienteCadastroFrame(cliente, newCliente -> {
            if (cliente == null) {
                tableModel.addCliente(newCliente);
            } else {
                tableModel.fireTableDataChanged();
            }
        });
        cadastroFrame.setVisible(true);
    }

    /**
     * Método para excluir o cliente selecionado.
     */
    private void deleteCliente() {
        int selectedRow = clienteTable.getSelectedRow();
        if (selectedRow >= 0) {
            ClienteDTO cliente = tableModel.getClienteAt(selectedRow);
            int input = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir esse cliente?", "Confirmação",
                    JOptionPane.YES_NO_OPTION);
            if (input != 0) {
                return;
            }

            try {
                clienteService.excluirCliente(cliente.getCodigo());
                tableModel.removeClienteAt(selectedRow);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir cliente.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.",
                    "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Abre o {@link ClienteCadastroFrame} passando o cliente selecionado para ser atualizado.
     */
    private void updateCliente() {
        int selectedRow = clienteTable.getSelectedRow();
        if (selectedRow >= 0) {
            ClienteDTO cliente = tableModel.getClienteAt(selectedRow);
            openClienteCadastroFrame(cliente);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para atualizar.",
                    "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
