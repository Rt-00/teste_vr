package com.teste_vr.server.services.cliente;

import com.teste_vr.server.domain.Cliente;
import com.teste_vr.server.dtos.cliente.CreateClienteDTO;
import com.teste_vr.server.dtos.cliente.ListClienteDTO;
import com.teste_vr.server.dtos.cliente.UpdateClienteDTO;
import com.teste_vr.server.mappers.ClienteMapper;
import com.teste_vr.server.repositories.ClienteRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service responsável por gerenciar operações relacionadas a Clientes.
 */
@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteValidationService clienteValidationService;
    private final ClienteMapper clienteMapper;

    /**
     * Construtor com argumentos.
     *
     * @param clienteRepository        Um {@link ClienteRepository}
     * @param clienteValidationService Um {@link ClienteValidationService}
     * @param clienteMapper            Um {@link ClienteMapper}
     */
    public ClienteService(ClienteRepository clienteRepository,
                          ClienteValidationService clienteValidationService,
                          ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteValidationService = clienteValidationService;
        this.clienteMapper = clienteMapper;
    }

    /**
     * Salva um novo Cliente no banco de dados.
     *
     * @param createClienteDTO Um {@link CreateClienteDTO} contendo os dados do Cliente a ser criado.
     * @return O {@link CreateClienteDTO} do Cliente criado.
     */
    public ListClienteDTO salvarCliente(CreateClienteDTO createClienteDTO) {
        this.clienteValidationService.validarClienteClienteDTO(createClienteDTO);
        Cliente cliente = this.clienteMapper.toEntity(createClienteDTO);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return clienteMapper.toListClienteDTO(clienteSalvo);
    }


    /**
     * Atualiza um Cliente existente.
     *
     * @param updateClienteDTO um {@link UpdateClienteDTO} contendo os dados do Cliente a ser atualizado.
     * @param id               O ID do Cliente a ser atualizado.
     * @return um {@link ListClienteDTO} com o Cliente atualizado.
     */
    public ListClienteDTO atualizarCliente(UpdateClienteDTO updateClienteDTO, Long id) {
        clienteValidationService.validarUpdateClienteDTO(updateClienteDTO, id);

        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () -> new ValidationException("Cliente não encontrado."));

        if (updateClienteDTO.nome() != null) {
            cliente.setNome(updateClienteDTO.nome());
        }

        if (updateClienteDTO.limiteCompra() != null) {
            cliente.setLimiteCompra(updateClienteDTO.limiteCompra());
        }

        cliente = clienteRepository.save(cliente);

        return clienteMapper.toListClienteDTO(cliente);
    }

    /**
     * Busca um Cliente pelo seu ID no banco de dados.
     *
     * @param id O ID do {@link Cliente} a ser buscado.
     * @return O {@link ListClienteDTO} do {@link Cliente} encontrado.
     * @throws ValidationException Se o Cliente não for encontrado.
     */
    public ListClienteDTO obterClientePorId(Long id) {
        return clienteRepository.findById(id).map(clienteMapper::toListClienteDTO)
                .orElseThrow(() -> new ValidationException("Cliente não encontrado"));
    }

    /**
     * Lista todos os Clientes do banco de dados.
     *
     * @return Uma {@link List} de {@link ListClienteDTO}.
     */
    public List<ListClienteDTO> listarClientes() {
        return this.clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toListClienteDTO)
                .toList();
    }

    /**
     * Delete um Cliente pelo Seu ID.
     *
     * @param id O ID do Cliente a ser deletado.
     * @throws ValidationException Se o Cliente não for encontrado.
     */
    public void excluirCliente(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new ValidationException("Cliente não encontrado");
        }

        clienteRepository.deleteById(id);
    }
}
