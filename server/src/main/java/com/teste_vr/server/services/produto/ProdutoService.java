package com.teste_vr.server.services.produto;

import com.teste_vr.server.domain.Produto;
import com.teste_vr.server.dtos.produto.CreateProdutoDTO;
import com.teste_vr.server.dtos.produto.ListProdutoDTO;
import com.teste_vr.server.dtos.produto.UpdateProdutoDTO;
import com.teste_vr.server.mappers.ProdutoMapper;
import com.teste_vr.server.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsável por gerir operações relacionadas a Produtos.
 */
@Service
public class ProdutoService {

    /**
     * Repositorio de Produtos.
     */
    private final ProdutoRepository produtoRepository;

    /**
     * Mapper de Clientes.
     */
    private final ProdutoMapper produtoMapper;

    /**
     * Service de validação de Produtos.
     */
    private final ProdutoValidationService produtoValidationService;

    /**
     * Construtor com Argumentos.
     *
     * @param produtoRepository Um {@link ProdutoRepository}.
     */
    public ProdutoService(ProdutoRepository produtoRepository,
                          ProdutoMapper produtoMapper,
                          ProdutoValidationService produtoValidationService) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
        this.produtoValidationService = produtoValidationService;
    }

    /**
     * Salva um novo Produto no banco de dados.
     *
     * @param createProdutoDTO Um {@link CreateProdutoDTO} contendo os dados do Produto a ser criado.
     * @return O {@link ListProdutoDTO} do Produto criado.
     */
    public ListProdutoDTO salvarProduto(CreateProdutoDTO createProdutoDTO) {
        this.produtoValidationService.validarCreateProdutoDTO(createProdutoDTO);

        if (produtoRepository.findByCodigo(createProdutoDTO.codigo()).isPresent()) {
            throw new DataIntegrityViolationException("Produto com o código: " + createProdutoDTO.codigo() +
                    " já cadastrado.");
        }

        Produto produto = this.produtoMapper.toEntity(createProdutoDTO);
        Produto clienteSalvo = produtoRepository.save(produto);

        return produtoMapper.toListProdutoDTO(clienteSalvo);
    }

    /**
     * Atualiza um Produto existente.
     *
     * @param updateProdutoDTO um {@link UpdateProdutoDTO} contendo os dados do Produto a ser atualizado.
     * @param codigo           O Código do Produto a ser atualizado.
     * @return um {@link ListProdutoDTO} com o Produto atualizado.
     */
    public ListProdutoDTO atualizarProduto(UpdateProdutoDTO updateProdutoDTO, Long codigo) {
        produtoValidationService.validarUpdateProdutoDTO(updateProdutoDTO, codigo);

        Produto produto = produtoRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException("Cliente não encontrado."));

        if (updateProdutoDTO.codigo() != null) {
            produto.setCodigo(updateProdutoDTO.codigo());
        }

        if (updateProdutoDTO.descricao() != null) {
            produto.setDescricao(updateProdutoDTO.descricao());
        }

        if (updateProdutoDTO.preco() != null) {
            produto.setPreco(updateProdutoDTO.preco());
        }

        produto = produtoRepository.save(produto);

        return produtoMapper.toListProdutDTO(produto);
    }

    /**
     * Lista todos os Produtos do banco de dados.
     *
     * @return Uma {@link List} de {@link ListProdutoDTO}.
     */
    public List<ListProdutoDTO> listarProdutos() {
        return this.produtoRepository.findAll()
                .stream()
                .map(produtoMapper::toListProdutoDTO)
                .toList();
    }

    /**
     * Delete um Produto pelo Seu Código.
     *
     * @param codigo O Código do Produto a ser deletado.
     * @throws ValidationException Se o Produto não for encontrado.
     */
    public void excluirProduto(Long codigo) {
        Produto produto = produtoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        produtoRepository.delete(produto);
    }
}
