package com.teste_vr.server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste_vr.server.dtos.cliente.CreateClienteDTO;
import com.teste_vr.server.dtos.cliente.ListClienteDTO;
import com.teste_vr.server.dtos.cliente.UpdateClienteDTO;
import com.teste_vr.server.services.cliente.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    /**
     * Testa o endpoint de criação de Cliente.
     * <p>
     * Verifica se um Cliente pode ser criado com sucesso e se a resposta é retornada com o status 201 Created.
     */
    @Test
    public void testSalvarCliente() throws Exception {
        CreateClienteDTO createClientDTO = new CreateClienteDTO("Teste", new BigDecimal("1000.0"));
        ListClienteDTO clientDTO = new ListClienteDTO(1L, "Teste", new BigDecimal("1000.0"));

        when(clienteService.salvarCliente(createClientDTO)).thenReturn(clientDTO);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createClientDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("Teste")))
                .andExpect(jsonPath("$.limiteCompra", is(1000.0)));
    }

    /**
     * Testa o endpoint de atualização de Cliente.
     * <p>
     * Verifica se um Cliente pode ser atualizado com sucesso e se a resposta é retornada com o status 200 OK.
     *
     * @throws Exception se ocorrer um erro na execução do teste
     */
    @Test
    public void testAtualizarCliente() throws Exception {
        Long clientAttId = 1L;
        UpdateClienteDTO updateClientDTO = new UpdateClienteDTO("Nome Atualizado", new BigDecimal("2000.0"));

        ListClienteDTO clientDTO = new ListClienteDTO(1L, "Nome Atualizado", new BigDecimal("2000.0"));

        when(clienteService.atualizarCliente(updateClientDTO, 1L))
                .thenReturn(clientDTO);

        mockMvc.perform(put("/clientes/{id}", clientAttId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateClientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome Atualizado"))
                .andExpect(jsonPath("$.limiteCompra").value(2000.0));
    }

    /**
     * Testa o endpoint de atualização de Cliente quando o Cliente não é encontrado.
     * <p>
     * Verifica se o retorno é 404 Not Found quando o Cliente não é encontrado.
     *
     * @throws Exception se ocorrer um erro na execução do teste
     */
    @Test
    public void testAtualizarClienteNaoEncontrado() throws Exception {
        Long clientAttId = 1L;
        UpdateClienteDTO updateClientDTO = new UpdateClienteDTO("Teste Atualizado",
                new BigDecimal("2000.0"));

        when(clienteService.atualizarCliente(updateClientDTO, clientAttId)).thenThrow(new EntityNotFoundException(
                "Cliente não encontrado."));

        mockMvc.perform(put("/clientes/{id}", clientAttId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateClientDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Cliente não encontrado.")));
    }

    /**
     * Testa o endpoint de exclusão de Cliente.
     * <p>
     * Verifica se um Cliente pode ser excluído com sucesso e se a resposta é retornada com o status 204 No Content.
     *
     * @throws Exception se ocorrer um erro na execução do teste
     */
    @Test
    public void testExcluirCliente() throws Exception {
        mockMvc.perform(delete("/clientes/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    /**
     * Testa o endpoint de exclusão de Cliente quando o Cliente não é encontrado.
     * <p>
     * Verifica se o retorno é 404 Not Found quando o Cliente não é encontrado.
     *
     * @throws Exception se ocorrer um erro na execução do teste
     */
    @Test
    public void testExcluirClienteNaoEncontrado() throws Exception {
        doThrow(new EntityNotFoundException("Cliente não encontrado com o ID: 1"))
                .when(clienteService).excluirCliente(1L);

        mockMvc.perform(delete("/clientes/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Cliente não encontrado com o ID: 1")));
    }

    /**
     * Testa o endpoint de listagem de clientes.
     * <p>
     * Verifica se a listagem de clientes é bem-sucedida e se a resposta é retornada com o status 200 OK.
     *
     * @throws Exception se ocorrer um erro na execução do teste
     */
    @Test
    public void testListarClientes() throws Exception {
        List<ListClienteDTO> clientes = List.of(
                new ListClienteDTO(1L, "Cliente 1", new BigDecimal("1000.0")),
                new ListClienteDTO(2L, "Cliente 2", new BigDecimal("2000.0"))
        );

        when(clienteService.listarClientes()).thenReturn(clientes);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome", is("Cliente 1")))
                .andExpect(jsonPath("$[0].limiteCompra", is(1000.0)))
                .andExpect(jsonPath("$[1].nome", is("Cliente 2")))
                .andExpect(jsonPath("$[1].limiteCompra", is(2000.0)));
    }

    /**
     * Testa o endpoint de listagem de cliente por ID.
     * <p>
     * Verifica se a busca por um cliente específico é bem-sucedida e se a resposta é retornada com o status 200 OK.
     *
     * @throws Exception se ocorrer um erro na execução do teste
     */
    @Test
    public void testListarClientePorId() throws Exception {
        ListClienteDTO clientDTO = new ListClienteDTO(1L, "Cliente 1", new BigDecimal("1000.0"));

        when(clienteService.obterClientePorId(1L)).thenReturn((clientDTO));

        mockMvc.perform(get("/clientes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Cliente 1")))
                .andExpect(jsonPath("$.limiteCompra", is(1000.0)));
    }

    /**
     * Testa o endpoint de listagem de cliente por ID.
     * <p>
     * Verifica se o retorno é 404 Not Found quando o Cliente não é encontrado.
     *
     * @throws Exception se ocorrer um erro na execução do teste
     */
    @Test
    public void testListarPorIdClienteNaoEncontrado() throws Exception {
        Long clienteId = 1L;

        when(clienteService.obterClientePorId(clienteId)).thenThrow(
                new EntityNotFoundException("Cliente não encontrado" + clienteId));

        mockMvc.perform(get("/clientes/{id}", clienteId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Cliente não encontrado" + clienteId)));
    }
}
