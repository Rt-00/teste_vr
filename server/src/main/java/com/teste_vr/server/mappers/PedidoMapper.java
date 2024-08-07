package com.teste_vr.server.mappers;

import com.teste_vr.server.domain.Cliente;
import com.teste_vr.server.domain.ItemPedido;
import com.teste_vr.server.domain.Pedido;
import com.teste_vr.server.dtos.cliente.ListClienteDTO;
import com.teste_vr.server.dtos.itempedido.ListItemPedidoDTO;
import com.teste_vr.server.dtos.pedido.CreatePedidoDTO;
import com.teste_vr.server.dtos.pedido.ListPedidoDTO;
import com.teste_vr.server.mappers.itempedido.ItemPedidoMapper;
import com.teste_vr.server.repositories.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    private final ClienteMapper clienteMapper;

    private final ItemPedidoMapper itemPedidoMapper;

    private final ClienteRepository clienteRepository;

    public PedidoMapper(ClienteMapper clienteMapper,
                        ItemPedidoMapper itemPedidoMapper,
                        ClienteRepository clienteRepository) {
        this.clienteMapper = clienteMapper;
        this.itemPedidoMapper = itemPedidoMapper;
        this.clienteRepository = clienteRepository;
    }

    public ListPedidoDTO toListPedidoDTO(Pedido pedido) {
        ListClienteDTO clienteDTO = clienteMapper.toListClienteDTO(pedido.getCliente());
        List<ListItemPedidoDTO> listaItensPedido = itemPedidoMapper.toListOfListItemPedidoDTO(pedido.getItens());

        return new ListPedidoDTO(
                pedido.getCodigo(),
                clienteDTO,
                listaItensPedido,
                pedido.getValorTotal()
        );
    }

    public Pedido toEntity(CreatePedidoDTO createPedidoDTO) {
        List<ItemPedido> listPedido = itemPedidoMapper.toListEntity(createPedidoDTO.itensPedido());

        Cliente cliente = clienteRepository.findByCodigo(createPedidoDTO.cliente().codigo()).orElseThrow(() ->
                new EntityNotFoundException("Cliente não econtrado"));

        Pedido pedido = new Pedido();
        pedido.setItens(listPedido);
        pedido.setCodigo(createPedidoDTO.codigo());
        pedido.setCliente(cliente);
        pedido.setValorTotal(createPedidoDTO.valorTotal());

        return pedido;
    }
}
