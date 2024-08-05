package com.teste_vr.client.ui.produto;

import com.teste_vr.client.dtos.produto.ProdutoDTO;
import com.teste_vr.client.services.produto.ProdutoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.function.Consumer;

public class ProdutoCadastroFrame extends JFrame {

    /**
     * TextField do Código.
     */
    private final JTextField codigoField;

    /**
     * TextField da Descrição.
     */
    private final JTextField descricaoField;

    /**
     * TextField do Preço.
     */
    private final JTextField precoField;

    /**
     * Botão Salvar.
     */
    private final JButton btnSalvar;

    /**
     * Botão Cancelar.
     */
    private final JButton btnCancelar;

    /**
     * DTO de Produto.
     */
    private final ProdutoDTO produto;

    /**
     * Serviço de Produto.
     */
    private final ProdutoService produtoService;

    /**
     * Consumer de Produto, responsável pelas operações entre telas.
     */
    private final Consumer<ProdutoDTO> onSaveCallback;

    /**
     * Construtor.
     *
     * @param produto        DTO de produto para atualização
     * @param onSaveCallback Consumer de Produto, responsável pelas operações entre telas.
     */
    public ProdutoCadastroFrame(ProdutoDTO produto, Consumer<ProdutoDTO> onSaveCallback) {
        this.produto = produto;
        this.onSaveCallback = onSaveCallback;

        produtoService = new ProdutoService();

        setTitle(produto == null ? "Criar Produto" : "Atualizar Produto");
        setSize(500, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblCodigo = new JLabel("Código:");
        codigoField = new JTextField(20);

        if (produto != null) {
            codigoField.setEnabled(false);
        }

        JLabel lblDescricao = new JLabel("Descrição:");
        descricaoField = new JTextField(20);

        JLabel lblPreco = new JLabel("Preço:");
        precoField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblCodigo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(codigoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblDescricao, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(descricaoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblPreco, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(precoField, gbc);

        // Create buttons
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isProdutoValido()) {
                    salvarProduto();
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        if (produto != null) {
            codigoField.setText(produto.getCodigo().toString());
            descricaoField.setText(produto.getDescricao());
            precoField.setText(produto.getPreco().toString());
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /*
     * Valida os dados de produto na tela.
     *
     * @return true se os dados são validos.
     */
    private boolean isProdutoValido() {
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

        if (descricaoField.getText().isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Descrição é obrigatória", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            new BigDecimal(precoField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Preço inválido", "Erro", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        return true;
    }

    /**
     * Realiza chamada ao service para persistir/atualizar o produto.
     */
    private void salvarProduto() {
        try {
            Long codigo = Long.parseLong(codigoField.getText());
            String descricao = descricaoField.getText();
            BigDecimal preco = new BigDecimal(precoField.getText());

            ProdutoDTO novoProduto = produto == null ? new ProdutoDTO() : produto;
            novoProduto.setCodigo(codigo);
            novoProduto.setDescricao(descricao);
            novoProduto.setPreco(preco);

            if (produto == null) {
                produtoService.salvarProduto(novoProduto);
            } else {
                produtoService.atualizarProduto(novoProduto);
            }

            onSaveCallback.accept(novoProduto);
            dispose();

            JOptionPane.showMessageDialog(this, "Produto salvo com sucesso!");
        } catch (NumberFormatException | IOException | InterruptedException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro de comunicação com o servidor:",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
