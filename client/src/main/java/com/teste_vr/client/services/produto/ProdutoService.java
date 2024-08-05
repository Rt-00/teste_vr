package com.teste_vr.client.services.produto;

import com.teste_vr.client.api.ProdutoAPIClient;
import com.teste_vr.client.dtos.api.ApiResponse;
import com.teste_vr.client.dtos.produto.ProdutoDTO;

import java.util.List;

public class ProdutoService {

    /**
     * API Client para Produtos.
     */
    private final ProdutoAPIClient produtoAPIClient;

    /**
     * Construtor padrão.
     */
    public ProdutoService() {
        this.produtoAPIClient = new ProdutoAPIClient();
    }

    /**
     * Utiliza o {@link ProdutoAPIClient} para obter os Produtos cadastrados.
     *
     * @return {@link ApiResponse} contendo uma {@link List} de {@link ProdutoDTO} cadastrados.
     * @throws Exception uma exceção qualquer.
     */
    public ApiResponse<List<ProdutoDTO>> getProdutosCadastrados() throws Exception {
        return produtoAPIClient.buscarProdutos();
    }

    /**
     * Utiliza o {@link ProdutoAPIClient} para a exclusão de Produto.
     *
     * @param codigo O Código do produto a ser excluido.
     * @throws Exception exceção qualquer.
     */
    public void excluirProduto(Long codigo) throws Exception {
        produtoAPIClient.excluirProduto(codigo);
    }

    /**
     * Utiliza o {@link ProdutoAPIClient} para a criação de Produto.
     *
     * @param produtoDTO {@link ProdutoDTO} a ser criado.
     * @throws Exception exceção qualquer.
     */
    public void salvarProduto(ProdutoDTO produtoDTO) throws Exception {
        produtoAPIClient.salvarProduto(produtoDTO);
    }

    /**
     * Utiliza o {@link ProdutoAPIClient} para a atualização de Produto.
     *
     * @param produtoDTO o {@link ProdutoDTO} do Produto que será atualizado.
     * @throws Exception outra exceção qualquer.
     */
    public void atualizarProduto(ProdutoDTO produtoDTO) throws Exception {
        produtoAPIClient.atualizarProduto(produtoDTO);
    }
}
