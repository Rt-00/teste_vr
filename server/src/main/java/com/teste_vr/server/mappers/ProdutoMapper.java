package com.teste_vr.server.mappers;

import com.teste_vr.server.domain.Produto;
import com.teste_vr.server.dtos.produto.CreateProdutoDTO;
import com.teste_vr.server.dtos.produto.ListProdutoDTO;
import org.springframework.stereotype.Component;

/**
 * Mapper responsável por realizar a conversão entre a entidade Produto e os seus DTOs.
 */
@Component
public class ProdutoMapper {
    /**
     * Converte uma entidade de Produto em um DTO de listagem de produto.
     *
     * @param produto A entidade {@link Produto} que será convertida em {@link ListProdutoDTO}
     * @return O {@link ListProdutoDTO} correspondente.
     */
    public ListProdutoDTO toListProdutoDTO(Produto produto) {
        return new ListProdutoDTO(
                produto.getCodigo(),
                produto.getDescricao(),
                produto.getPreco()
        );
    }

    /**
     * Converte um DTO de criação de Produto numa entidade Produto.
     *
     * @param createProdutoDTO o {@link CreateProdutoDTO} que será convertido {@link Produto}.
     * @return A entidade {@link Produto} correspondente.
     */
    public Produto toEntity(CreateProdutoDTO createProdutoDTO) {
        Produto produto = new Produto();
        produto.setCodigo(createProdutoDTO.codigo());
        produto.setDescricao(createProdutoDTO.descricao());
        produto.setPreco(createProdutoDTO.preco());

        return produto;
    }

    public CreateProdutoDTO toCreateProdutoDTO(ListProdutoDTO listProdutoDTO) {
        return new CreateProdutoDTO(
                listProdutoDTO.codigo(),
                listProdutoDTO.descricao(),
                listProdutoDTO.preco()
        );
    }

    /**
     * Converte uma entidade Produto em um DTO de Listagem de Produto.
     *
     * @param produto Um {@link Produto}.
     * @return Um {@link ListProdutoDTO}.
     */
    public ListProdutoDTO toListProdutDTO(Produto produto) {
        return new ListProdutoDTO(
                produto.getCodigo(),
                produto.getDescricao(),
                produto.getPreco()
        );
    }
}
