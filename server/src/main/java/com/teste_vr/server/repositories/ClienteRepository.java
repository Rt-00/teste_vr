package com.teste_vr.server.repositories;

import com.teste_vr.server.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório para {@link Cliente}.
 * <p>
 * Fornece operações CRUD básicas e consultas personalizadas. É possível expandir para realizar
 * operações customizadas.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCodigo(Long codigo);
}
