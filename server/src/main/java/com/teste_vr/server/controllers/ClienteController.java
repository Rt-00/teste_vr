package com.teste_vr.server.controllers;

import com.teste_vr.server.dtos.cliente.CreateClienteDTO;
import com.teste_vr.server.dtos.cliente.ListClienteDTO;
import com.teste_vr.server.dtos.cliente.UpdateClienteDTO;
import com.teste_vr.server.services.cliente.ClienteService;
import com.teste_vr.server.domain.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller responsável por gerenciar as operações HTTP relacionadas a Clientes.
 */
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Construtor com Argumentos.
     *
     * @param clienteService Um {@link ClienteService}
     */
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Cria um novo {@link Cliente}.
     *
     * @param createClienteDTO Um {@link CreateClienteDTO} contendo os dados do Cliente a ser criado.
     * @return Uma {@link ResponseEntity} com o {@link ListClienteDTO} do Cliente criado.
     */
    @PostMapping
    public ResponseEntity<ListClienteDTO> salvarCliente(@RequestBody CreateClienteDTO createClienteDTO) {
        ListClienteDTO cliente = clienteService.salvarCliente(createClienteDTO);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    /**
     * Atualiza um {@link Cliente} com base no seu ID.
     *
     * @param id               O ID do Cliente que será atualizado.
     * @param updateClienteDTO Um {@link UpdateClienteDTO} contendo os dados do Cliente a ser atualizado.
     * @return Uma {@link ResponseEntity} com o {@link ListClienteDTO} do Cliente atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ListClienteDTO> atualizarCliente(@PathVariable Long id,
                                                           @RequestBody UpdateClienteDTO updateClienteDTO) {
        ListClienteDTO clienteAtualizado = clienteService.atualizarCliente(updateClienteDTO, id);
        return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
    }

    /**
     * Lista todos os Clientes.
     *
     * @return Uma {@link List} de {@link ListClienteDTO}.
     */
    @GetMapping
    public ResponseEntity<List<ListClienteDTO>> listarClientes() {
        List<ListClienteDTO> cliente = clienteService.listarClientes();
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    /**
     * Busca um Cliente pelo seu ID.
     *
     * @param id O ID do Cliente a ser buscado.
     * @return O {@link ListClienteDTO} do Cliente encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ListClienteDTO> obterClientePorId(@PathVariable Long id) {
        return new ResponseEntity<>(clienteService.obterClientePorId(id), HttpStatus.OK);
    }

    /**
     * Deleta um Cliente pelo seu ID.
     *
     * @param id O ID do cliente a ser deletado.
     * @return Uma resposta vazia indicando sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        clienteService.excluirCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
