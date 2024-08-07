package com.teste_vr.server.services.pedido;

import com.teste_vr.server.domain.Cliente;
import com.teste_vr.server.domain.ItemPedido;
import com.teste_vr.server.domain.Pedido;
import com.teste_vr.server.domain.Produto;
import com.teste_vr.server.dtos.pedido.CreatePedidoDTO;
import com.teste_vr.server.dtos.pedido.ListPedidoDTO;
import com.teste_vr.server.dtos.pedido.UpdatePedidoDTO;
import com.teste_vr.server.mappers.PedidoMapper;
import com.teste_vr.server.mappers.itempedido.ItemPedidoMapper;
import com.teste_vr.server.repositories.ClienteRepository;
import com.teste_vr.server.repositories.ItemPedidoRepository;
import com.teste_vr.server.repositories.PedidoRepository;
import com.teste_vr.server.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoMapper pedidoMapper;
    private final ItemPedidoMapper itemPedidoMapper;
    private final PedidoValidationService pedidoValidationService;
    private final ProdutoRepository produtoRepository;


    public PedidoService(
            PedidoRepository pedidoRepository,
            ItemPedidoRepository itemPedidoRepository,
            PedidoMapper pedidoMapper,
            ClienteRepository clienteRepository,
            ItemPedidoMapper itemPedidoMapper,
            PedidoValidationService pedidoValidationService,
            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.clienteRepository = clienteRepository;
        this.itemPedidoMapper = itemPedidoMapper;
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoValidationService = pedidoValidationService;
        this.produtoRepository = produtoRepository;
    }

    public List<ListPedidoDTO> listarProdutos() {
        return this.pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toListPedidoDTO)
                .toList();
    }

    /**
     * Delete um Pedido pelo Seu Código.
     *
     * @param codigo O Código do Pedido a ser deletado.
     * @throws ValidationException Se o Pedido não for encontrado.
     */
    public void excluirProduto(Long codigo) {
        Pedido pedido = this.pedidoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        pedidoRepository.delete(pedido);
    }

    public ListPedidoDTO salvarPedido(CreatePedidoDTO createPedidoDTO) {
        this.pedidoValidationService.validarCreatePedidoDTO(createPedidoDTO);

        if (pedidoRepository.findByCodigo(createPedidoDTO.codigo()).isPresent()) {
            throw new DataIntegrityViolationException("Pedido com o código: " + createPedidoDTO.codigo() +
                    " já cadastrado.");
        }

        List<ItemPedido> listItemPedido = itemPedidoMapper.toListEntity(createPedidoDTO.itensPedido());

        Pedido pedido = pedidoMapper.toEntity(createPedidoDTO);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        listItemPedido.forEach(ip -> ip.setPedido(pedidoSalvo));

        listItemPedido.forEach(itemPedidoRepository::save);

        return pedidoMapper.toListPedidoDTO(pedidoSalvo);
    }

    public ListPedidoDTO atualizarPedido(UpdatePedidoDTO updatePedidoDTO, Long codigo) {
        pedidoValidationService.validarUpdatePedidoDTO(updatePedidoDTO, codigo);

        Pedido pedido = pedidoRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException("Pedido não encontrado."));

        if (updatePedidoDTO.codigo() != null) {
            pedido.setCodigo(codigo);
        }

        Cliente cliente = clienteRepository.findByCodigo(updatePedidoDTO.codigo()).orElseThrow(
                () -> new EntityNotFoundException("Cliente não encontrado."));
        if (updatePedidoDTO.cliente() != null) {
            pedido.setCliente(cliente);
        }

        if (updatePedidoDTO.valorTotal() != null) {
            pedido.setValorTotal(updatePedidoDTO.valorTotal());
        }

        itemPedidoRepository.findByPedidoId(pedido.getId()).forEach(itemPedidoRepository::delete);
        if (!updatePedidoDTO.itensPedido().isEmpty()) {
            List<ItemPedido> itensPedidoDTO = itemPedidoMapper.toListEntity(updatePedidoDTO.itensPedido());
            itensPedidoDTO.forEach(ip -> ip.setPedido(pedido));
            itensPedidoDTO.forEach(itemPedidoRepository::save);
        }

        pedidoRepository.save(pedido);

        return pedidoMapper.toListPedidoDTO(pedido);
    }
}
