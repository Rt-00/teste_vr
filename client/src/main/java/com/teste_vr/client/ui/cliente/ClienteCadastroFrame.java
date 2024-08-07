package com.teste_vr.client.ui.cliente;

import com.teste_vr.client.dtos.clientes.ClienteDTO;
import com.teste_vr.client.services.cliente.ClienteService;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Consumer;

/**
 * Frame utilizado para criação/atualização de um Cliente.
 */
public class ClienteCadastroFrame extends JFrame {
    /**
     * TextField de Código.
     */
    private final JTextField codigoField;

    /**
     * TextField de Nome.
     */
    private final JTextField nomeField;

    /**
     * TextField do Limite de Compra.
     */
    private final JTextField limiteCompraField;

    /**
     * Botão Salvar.
     */
    private final JButton btnSalvar;

    /**
     * Calendário para Datas.
     */
    private final JDateChooser dateChooser; // Componente de calendário para a data

    /**
     * Serviço de Cliente.
     */
    private final ClienteService clienteService;

    /**
     * DTO de Cliente.
     */
    private final ClienteDTO cliente;

    /**
     * Consumer de Cliente, responsável pelas operações entre telas.
     */
    private final Consumer<ClienteDTO> onSaveCallback;

    /**
     * Construtor.
     *
     * @param cliente        DTO de Cliente para atualização.
     * @param onSaveCallback Consumer de Cliente, para as operações entre telas.
     */
    public ClienteCadastroFrame(ClienteDTO cliente, Consumer<ClienteDTO> onSaveCallback) {
        this.cliente = cliente;
        this.onSaveCallback = onSaveCallback;

        clienteService = new ClienteService();

        setTitle(cliente == null ? "Criar Cliente" : "Atualizar Cliente");
        setSize(500, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel codigoLabel = new JLabel("Código:");
        codigoField = new JTextField(20);

        if (cliente != null) {
            codigoField.setEnabled(false);
        }

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField(20);

        JLabel limiteCompraLabel = new JLabel("Limite de Compra:");
        limiteCompraField = new JTextField(20);

        JLabel lblDataFechamentoFatura = new JLabel("Data Fechamento Fatura:");

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy"); // Formato da data

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(codigoLabel, gbc);

        gbc.gridx = 1;
        add(codigoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nomeLabel, gbc);

        gbc.gridx = 1;
        add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(limiteCompraLabel, gbc);

        gbc.gridx = 1;
        add(limiteCompraField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblDataFechamentoFatura, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(dateChooser, gbc);

        // Create buttons
        btnSalvar = new JButton(cliente == null ? "Criar" : "Atualizar");
        JButton cancelarButton = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSalvar);
        buttonPanel.add(cancelarButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (isClienteValido()) {
                        salvarCliente();
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        if (cliente != null) {
            codigoField.setText(cliente.getCodigo().toString());
            nomeField.setText(cliente.getNome());
            limiteCompraField.setText(cliente.getLimiteCompra().toString());
            dateChooser.setDate(cliente.getDataFechamentoFatura());
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Valida os dados de cliente na tela.
     *
     * @return true se os dados são validos.
     */
    private boolean isClienteValido() {
        if (nomeField.getText().isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Nome é obrigatório", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (codigoField.getText().isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Código é obrigatório", "Erro", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        try {
            Long.parseLong(codigoField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Código inválido", "Erro", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        if (limiteCompraField.getText().isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Limite de Compra é obrigatório", "Erro", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        try {
            new BigDecimal(limiteCompraField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Limite de Compra inválido", "Erro", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this,
                    "Data de Fechamento da Fatura obrigatória", "Erro", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        return true;
    }

    /**
     * Realiza chamada ao service para persistir/atualizar o cliente.
     */
    private void salvarCliente() {
        try {
            Long codigo = Long.parseLong(codigoField.getText());
            String nome = nomeField.getText();
            BigDecimal limiteCompra = new BigDecimal(limiteCompraField.getText());
            Date dataFechamentoFatura = dateChooser.getDate();

            ClienteDTO novoCliente = cliente == null ? new ClienteDTO() : cliente;
            novoCliente.setCodigo(codigo);
            novoCliente.setNome(nome);
            novoCliente.setLimiteCompra(limiteCompra);
            novoCliente.setDataFechamentoFatura(dataFechamentoFatura);

            if (cliente == null) {
                clienteService.salvarCliente(novoCliente);
            } else {
                clienteService.atualizarCliente(novoCliente);
            }

            onSaveCallback.accept(novoCliente);
            dispose();

            JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
        } catch (NumberFormatException | IOException | InterruptedException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro de comunicação com o servidor:",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
