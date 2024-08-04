package com.teste_vr.server.mappers;

import com.teste_vr.server.domain.Cliente;
import com.teste_vr.server.dtos.cliente.CreateClienteDTO;
import com.teste_vr.server.dtos.cliente.ListClienteDTO;
import com.teste_vr.server.dtos.cliente.UpdateClienteDTO;
import org.springframework.stereotype.Component;

/**
 * Mapper responsável por realizar a conversão entre a entidade Cliente e os seus DTOs.
 */
@Component
public class ClienteMapper {

    /**
     * Converte um DTO de criação de Cliente numa entidade Cliente.
     *
     * @param createClienteDTO O {@link CreateClienteDTO} que será convertido {@link Cliente}.
     * @return A entidade {@link Cliente} correspondente.
     */
    public Cliente toEntity(CreateClienteDTO createClienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setCodigo(createClienteDTO.codigo());
        cliente.setNome(createClienteDTO.nome());
        cliente.setLimiteCompra(createClienteDTO.limiteCompra());
        cliente.setDataFechamentoFatura(createClienteDTO.dataFechamentoFatura());
        return cliente;
    }

    /**
     * Converte um DTO de atualização de Cliente numa entidade Cliente.
     *
     * @param updateClienteDTO O {@link UpdateClienteDTO} que será convertido em {@link Cliente}.
     * @param codigo           O Código do Cliente.
     * @return A entidade {@link Cliente} correspondente.
     */
    public Cliente toEntity(UpdateClienteDTO updateClienteDTO, Long codigo) {
        Cliente cliente = new Cliente();
        cliente.setCodigo(updateClienteDTO.codigo());
        cliente.setNome(updateClienteDTO.nome());
        cliente.setLimiteCompra(updateClienteDTO.limiteCompra());
        cliente.setDataFechamentoFatura(updateClienteDTO.dataFechamentoFatura());
        return cliente;
    }

    /**
     * Converte uma entidade de Cliente em um DTO de listagem de Cliente.
     *
     * @param cliente A entidade {@link Cliente} que será convertida em {@link ListClienteDTO}
     * @return O {@link ListClienteDTO} correspondente.
     */
    public ListClienteDTO toListClienteDTO(Cliente cliente) {
        return new ListClienteDTO(
                cliente.getCodigo(),
                cliente.getNome(),
                cliente.getLimiteCompra(),
                cliente.getDataFechamentoFatura()
        );
    }
}
