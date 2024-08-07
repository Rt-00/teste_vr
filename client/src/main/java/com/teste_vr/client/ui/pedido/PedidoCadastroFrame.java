package com.teste_vr.client.ui.pedido;

import com.teste_vr.client.dtos.api.ApiResponse;
import com.teste_vr.client.dtos.clientes.ClienteDTO;
import com.teste_vr.client.dtos.itempedido.ItemPedidoDTO;
import com.teste_vr.client.dtos.pedidos.PedidoDTO;
import com.teste_vr.client.dtos.produto.ProdutoDTO;
import com.teste_vr.client.services.cliente.ClienteService;
import com.teste_vr.client.services.pedido.PedidoService;
import com.teste_vr.client.ui.components.ItemPedidoTableModel;
import com.teste_vr.client.ui.produto.ProdutoSelecionarFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class PedidoCadastroFrame extends JFrame {
    private final PedidoService pedidoService;
    private JComboBox<ClienteDTO> comboBoxCliente;
    private JTextField textFieldValorTotal;
    private JTextField textFieldCodigo;
    private JTable itensPedidoTable;
    private final ItemPedidoTableModel tableModel;
    private final PedidoDTO pedido;
    /**
     * Consumer de Pedido, responsável pelas operações entre telas.
     */
    private final Consumer<PedidoDTO> onSaveCallback;

    public PedidoCadastroFrame(PedidoDTO pedido, Consumer<PedidoDTO> onSaveCallback) {
        this.pedido = pedido;
        this.pedidoService = new PedidoService();
        this.onSaveCallback = onSaveCallback;

        setTitle(pedido == null ? "Novo Pedido" : "Atualizar Pedido");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de Formulário
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelCodigo = new JLabel("Codgo: ");
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelFormulario.add(labelCodigo, gbc);

        textFieldCodigo = new JTextField();

        if (pedido != null) {
            textFieldCodigo.setEnabled(false);
        }

        gbc.gridx = 2;
        gbc.gridy = 0;
        panelFormulario.add(textFieldCodigo, gbc);

        // ComboBox de Cliente
        JLabel labelCliente = new JLabel("Cliente:");
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelFormulario.add(labelCliente, gbc);

        comboBoxCliente = new JComboBox<>();
        gbc.gridx = 2;
        gbc.gridy = 1;
        panelFormulario.add(comboBoxCliente, gbc);
        getClientes();

        // Campo de Valor Total
        JLabel labelValorTotal = new JLabel("Valor Total:");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelFormulario.add(labelValorTotal, gbc);

        textFieldValorTotal = new JTextField();
        textFieldValorTotal.setEditable(false);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panelFormulario.add(textFieldValorTotal, gbc);

        // Tabela de Itens do Pedido
        tableModel = new ItemPedidoTableModel(new ArrayList<>(), this);
        itensPedidoTable = new JTable(tableModel);

        JScrollPane scrollPaneItens = new JScrollPane(itensPedidoTable);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panelFormulario.add(scrollPaneItens, gbc);

        // Botões
        JPanel panelBotoes = new JPanel();
        JButton btnAdicionarItem = new JButton("Adicionar Item");
        JButton btnRemoveItem = new JButton("Remover Item");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        panelBotoes.add(btnAdicionarItem);
        panelBotoes.add(btnRemoveItem);
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnCancelar);

        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);

        // Eventos dos Botões
        btnAdicionarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCadastroItem();
            }
        });

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPedidoValido()) {
                    salvarPedido();
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnRemoveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerProdutoSelecionado();
            }
        });

        // Se for edição, carregar os dados do pedido
        if (pedido != null) {
            textFieldCodigo.setText(pedido.getCodigo().toString());
            comboBoxCliente.setSelectedItem(pedido.getCliente());
            for (ItemPedidoDTO item : pedido.getItensPedido()) {
                tableModel.adicionarProdutoPedido(new ItemPedidoDTO(
                        item.getProduto().getCodigo(),
                        item.getProduto(),
                        item.getQuantidade(),
                        item.getPreco()
                ));
            }

            atualizarValorTotal();
        }
    }

    private void salvarPedido() {
        try {
            ClienteDTO clienteSelecionado = (ClienteDTO) comboBoxCliente.getSelectedItem();
            String valorTotal = textFieldValorTotal.getText().replace("R$", "").trim();

            PedidoDTO novoPedido = pedido == null ? new PedidoDTO() : pedido;
            novoPedido.setCodigo(Long.parseLong(textFieldCodigo.getText()));
            novoPedido.setCliente(clienteSelecionado);
            novoPedido.setValorTotal(new BigDecimal(valorTotal));
            novoPedido.setItensPedido(tableModel.getItensPedidos());
            novoPedido.setDataHora(new Date());

            if (pedido == null) {
                pedidoService.salvarPedido(novoPedido);
            } else {
                pedidoService.atualizarPedido(novoPedido);
            }

            onSaveCallback.accept(novoPedido);
            dispose();

            JOptionPane.showMessageDialog(this, "Pedido salvo com sucesso!");
        } catch (NumberFormatException | IOException | InterruptedException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro de comunicação com o servidor:",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isPedidoValido() {
        if (textFieldCodigo.getText().isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Código é obrigatório", "Erro", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        try {
            long codigo = Long.parseLong(textFieldCodigo.getText());
            if (codigo <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Código deve ser positivo", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Código inválido", "Erro", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        ClienteDTO clienteSelecionado = (ClienteDTO) comboBoxCliente.getSelectedItem();
        if (clienteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.", "Erro",
                    JOptionPane.ERROR_MESSAGE);

            return false;
        }

        if (tableModel.getItensPedidos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lista de Itens vazia", "Erro",
                    JOptionPane.ERROR_MESSAGE);

            return false;
        }

        return true;
    }

    private void removerProdutoSelecionado() {
        int rowIndex = itensPedidoTable.getSelectedRow();
        if (rowIndex >= 0) {
            tableModel.removerProdutoPedido(rowIndex);
            atualizarValorTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarCadastroItem() {
        ProdutoSelecionarFrame produtoSelecionarFrame = new ProdutoSelecionarFrame(this);
        produtoSelecionarFrame.setVisible(true);
    }

    public void atualizarValorTotal() {
        BigDecimal valorTotal = tableModel.calcularValorTotal();
        textFieldValorTotal.setText(String.format("R$ %.2f", valorTotal));
    }

    private void getClientes() {
        ClienteService clienteService = new ClienteService();
        ApiResponse<List<ClienteDTO>> apiResponse = null;
        try {
            apiResponse = clienteService.getClienteCadastrados();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter Clientes da API. Status: " +
                    apiResponse.getStatus(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        for (ClienteDTO cliente : apiResponse.getData()) {
            comboBoxCliente.addItem(cliente);
        }
    }

    public void adicionarProduto(ProdutoDTO produto) {
        String quantidadeString = JOptionPane.showInputDialog(this, "Digite a quantidade:");
        if (quantidadeString != null) {
            try {
                int quantidade = Integer.parseInt(quantidadeString);
                BigDecimal preco = produto.getPreco();
                ItemPedidoDTO itemPedido = new ItemPedidoDTO(
                        produto.getCodigo(),
                        produto,
                        quantidade,
                        produto.getPreco().multiply(new BigDecimal(quantidade))
                );
                tableModel.adicionarProdutoPedido(itemPedido);

                atualizarValorTotal();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Quantidade inválida.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
