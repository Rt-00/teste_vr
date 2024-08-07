package com.teste_vr.server.controllers;

import com.teste_vr.server.domain.Produto;
import com.teste_vr.server.dtos.produto.CreateProdutoDTO;
import com.teste_vr.server.dtos.produto.ListProdutoDTO;
import com.teste_vr.server.dtos.produto.UpdateProdutoDTO;
import com.teste_vr.server.services.produto.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por gerir as operações HTTP relacionadas a Produto.
 */
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    /**
     * Serviço de Produtos
     */
    private final ProdutoService produtoService;

    /**
     * Construtor com Argumentos.
     */
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    /**
     * Cria um {@link Produto}.
     *
     * @param createProdutoDTO Um {@link CreateProdutoDTO} contendo os dados do Produto a ser criado.
     * @return Uma {@link ResponseEntity} com o {@link ListProdutoDTO} do Produto criado.
     */
    @PostMapping
    public ResponseEntity<ListProdutoDTO> salvarCliente(@RequestBody CreateProdutoDTO createProdutoDTO) {
        ListProdutoDTO produto = produtoService.salvarProduto(createProdutoDTO);
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    /**
     * Lista todos os Produtos.
     *
     * @return Uma {@link List} de {@link ListProdutoDTO}.
     */
    @GetMapping
    public ResponseEntity<List<ListProdutoDTO>> listarProdutos() {
        List<ListProdutoDTO> cliente = produtoService.listarProdutos();
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    /**
     * Atualiza um {@link Produto} com base no seu Código.
     *
     * @param codigo           O Código do Produto que será atualizado.
     * @param UpdateProdutoDTO Um {@link UpdateProdutoDTO} contendo os dados do Produto a ser atualizado.
     * @return Uma {@link ResponseEntity} com o {@link ListProdutoDTO} do Produto atualizado.
     */
    @PutMapping("/{codigo}")
    public ResponseEntity<ListProdutoDTO> atualizarProduto(@PathVariable Long codigo,
                                                           @RequestBody UpdateProdutoDTO UpdateProdutoDTO) {
        ListProdutoDTO produtoAtualizado = produtoService.atualizarProduto(UpdateProdutoDTO, codigo);
        return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);
    }

    /**
     * Deleta um Produto pelo seu Código.
     *
     * @param codigo O Código do Produto a ser deletado.
     * @return Uma resposta vazia indicando sucesso.
     */
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long codigo) {
        produtoService.excluirProduto(codigo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
