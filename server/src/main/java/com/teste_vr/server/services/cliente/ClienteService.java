package com.teste_vr.server.services.cliente;

import com.teste_vr.server.domain.Cliente;
import com.teste_vr.server.dtos.cliente.CreateClienteDTO;
import com.teste_vr.server.dtos.cliente.ListClienteDTO;
import com.teste_vr.server.dtos.cliente.UpdateClienteDTO;
import com.teste_vr.server.mappers.ClienteMapper;
import com.teste_vr.server.repositories.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsável por gerenciar operações relacionadas a Clientes.
 */
@Service
public class ClienteService {
    /**
     * Repositório de Clientes.
     */
    private final ClienteRepository clienteRepository;

    /**
     * Service de validação de Clientes.
     */
    private final ClienteValidationService clienteValidationService;

    /**
     * Mapper de Clientes.
     */
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
     * @return O {@link ListClienteDTO} do Cliente criado.
     */
    public ListClienteDTO salvarCliente(CreateClienteDTO createClienteDTO) {
        this.clienteValidationService.validarCreateClienteDTO(createClienteDTO);

        if (clienteRepository.findByCodigo(createClienteDTO.codigo()).isPresent()) {
            throw new DataIntegrityViolationException("Cliente com o código: " + createClienteDTO.codigo() +
                    " já cadastrado.");
        }

        Cliente cliente = this.clienteMapper.toEntity(createClienteDTO);
        Cliente clienteSalvo = clienteRepository.save(cliente);

        return clienteMapper.toListClienteDTO(clienteSalvo);
    }


    /**
     * Atualiza um Cliente existente.
     *
     * @param updateClienteDTO um {@link UpdateClienteDTO} contendo os dados do Cliente a ser atualizado.
     * @param codigo           O Código do Cliente a ser atualizado.
     * @return um {@link ListClienteDTO} com o Cliente atualizado.
     */
    public ListClienteDTO atualizarCliente(UpdateClienteDTO updateClienteDTO, Long codigo) {
        clienteValidationService.validarUpdateClienteDTO(updateClienteDTO, codigo);

        Cliente cliente = clienteRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException("Cliente não encontrado."));

        if (updateClienteDTO.codigo() != null) {
            cliente.setCodigo(updateClienteDTO.codigo());
        }

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
     * Busca um Cliente pelo seu Código no banco de dados.
     *
     * @param codigo O Código do {@link Cliente} a ser buscado.
     * @return O {@link ListClienteDTO} do {@link Cliente} encontrado.
     * @throws ValidationException Se o Cliente não for encontrado.
     */
    public ListClienteDTO obterClientePorCodigo(Long codigo) {
        return clienteRepository.findByCodigo(codigo).map(clienteMapper::toListClienteDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
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
     * Delete um Cliente pelo Seu Código.
     *
     * @param codigo O Código do Cliente a ser deletado.
     * @throws ValidationException Se o Cliente não for encontrado.
     */
    public void excluirCliente(Long codigo) {
        Cliente cliente = clienteRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        clienteRepository.delete(cliente);
    }
}
