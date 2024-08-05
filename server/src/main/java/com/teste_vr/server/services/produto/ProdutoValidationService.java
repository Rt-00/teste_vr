package com.teste_vr.server.services.produto;

import com.teste_vr.server.dtos.produto.CreateProdutoDTO;
import com.teste_vr.server.dtos.produto.UpdateProdutoDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service responsável pela validação de dados do produto.
 */
@Service
public class ProdutoValidationService {
    /**
     * Valida o DTO de criação de Produto.
     *
     * @param createProdutoDTO O {@link CreateProdutoDTO} contendo os dados do Produto a ser validado.
     * @throws IllegalArgumentException Se algum dado do Produto for inválido.
     */
    public void validarCreateProdutoDTO(CreateProdutoDTO createProdutoDTO) {
        if (createProdutoDTO.codigo() == null || createProdutoDTO.codigo() <= 0) {
            throw new IllegalArgumentException("O código não pode ser nulo ou vazio.");
        }

        if (createProdutoDTO.descricao() == null || createProdutoDTO.descricao().isBlank()) {
            throw new IllegalArgumentException("A descrição não pode ser nula ou vazia.");
        }

        if (createProdutoDTO.preco() == null || createProdutoDTO.preco().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço não pode ser nulo ou negativo.");
        }
    }

    /**
     * Valida o DTO de atualização de Produto.
     *
     * @param updateProdutoDTO O {@link UpdateProdutoDTO} contendo os dados do Produto a ser validado.
     * @throws IllegalArgumentException Se algum dado do Produto for inválido.
     */
    public void validarUpdateProdutoDTO(UpdateProdutoDTO updateProdutoDTO, Long codigo) {
        if (codigo == null || codigo <= 0) {
            throw new IllegalArgumentException("O código não pode ser nulo ou vazio.");
        }

        if (updateProdutoDTO.descricao() != null && updateProdutoDTO.descricao().isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode ser nula ou vazia.");
        }

        if (updateProdutoDTO.preco() != null && updateProdutoDTO.preco().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço não pode ser nulo ou negativo.");
        }
    }
}
