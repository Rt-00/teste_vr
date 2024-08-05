package com.teste_vr.server.repositories;

import com.teste_vr.server.domain.Cliente;
import com.teste_vr.server.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório para {@link Produto}.
 * <p>
 * Fornece operações CRUD básicas e consultas personalizadas. É possível expandir para realizar
 * operações customizadas.
 */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByCodigo(Long codigo);
}
