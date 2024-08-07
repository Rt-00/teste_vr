package com.teste_vr.server.mappers.itempedido;

import com.teste_vr.server.domain.ItemPedido;
import com.teste_vr.server.domain.Produto;
import com.teste_vr.server.dtos.itempedido.CreateItemPedidoDTO;
import com.teste_vr.server.dtos.itempedido.ListItemPedidoDTO;
import com.teste_vr.server.dtos.produto.CreateProdutoDTO;
import com.teste_vr.server.mappers.ProdutoMapper;
import com.teste_vr.server.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemPedidoMapper {

    private final ProdutoMapper produtoMapper;
    private final ProdutoRepository produtoRepository;

    public ItemPedidoMapper(ProdutoMapper produtoMapper, ProdutoRepository produtoRepository) {
        this.produtoMapper = produtoMapper;
        this.produtoRepository = produtoRepository;
    }

    public List<ListItemPedidoDTO> toListOfListItemPedidoDTO(List<ItemPedido> itensPedidos) {
        List<ListItemPedidoDTO> listaPedidos = new ArrayList<>();

        itensPedidos.forEach(i -> listaPedidos.add(
                new ListItemPedidoDTO(
                        i.getCodigo(),
                        produtoMapper.toListProdutoDTO(i.getProduto()),
                        i.getQuantidade(),
                        i.getPreco())));

        return listaPedidos;
    }

    public List<ItemPedido> toListEntity(List<CreateItemPedidoDTO> listCreateItempedidoDTO) {
        List<ItemPedido> listItensPedido = new ArrayList<>();

        for (CreateItemPedidoDTO createItemPedidoDTO : listCreateItempedidoDTO) {
            CreateProdutoDTO createProdutoDTO = produtoMapper.toCreateProdutoDTO(createItemPedidoDTO.produto());

            Produto produto = produtoRepository.findByCodigo(createItemPedidoDTO.produto().codigo()).orElseThrow(() ->
                    new EntityNotFoundException("Produto não econtrado"));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setCodigo(createItemPedidoDTO.codigo());
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(createItemPedidoDTO.quantidade());
            itemPedido.setPreco(createItemPedidoDTO.preco());

            listItensPedido.add(itemPedido);
        }

        return listItensPedido;
    }
}
