package com.teste_vr.client.ui;

import com.teste_vr.client.dtos.api.ApiResponse;
import com.teste_vr.client.dtos.clientes.ClienteDTO;
import com.teste_vr.client.services.cliente.ClienteService;
import com.teste_vr.client.ui.cliente.ClienteScreenFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Menu Principal da Aplicação.
 */
public class MainMenu extends JFrame {

    /**
     * Constutor padrão.
     */
    public MainMenu() {
        setTitle("Menu Principal");
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create buttons
        JButton clienteButton = new JButton("Cadastro de Cliente");
        JButton produtoButton = new JButton("Cadastro de Produto");
        JButton pedidoButton = new JButton("Cadastro de Pedido");

        // Add buttons to the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(clienteButton, gbc);

        gbc.gridy = 1;
        add(produtoButton, gbc);

        gbc.gridy = 2;
        add(pedidoButton, gbc);

        clienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCadastroCliente();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Abre a Tela de Cadastro de Clientes.
     */
    private void abrirCadastroCliente() {
        List<ClienteDTO> clientes;
        try {
            ClienteService clienteApiService = new ClienteService();
            ApiResponse<List<ClienteDTO>> response = clienteApiService.getClienteCadastrados();

            if (response.getStatus() == 200) {
                clientes = response.getData();
                SwingUtilities.invokeLater(() -> new ClienteScreenFrame(clientes).setVisible(true));
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao obter clientes da API. Status: " +
                        response.getStatus(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter clientes da API.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}
