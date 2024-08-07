package com.teste_vr.server.repositories;

import com.teste_vr.server.domain.Pedido;
import com.teste_vr.server.dtos.pedido.ListPedidoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByCodigo(Long codigo);

    @Query("SELECT SUM(p.valorTotal) FROM Pedido p WHERE p.cliente.codigo = :clienteId AND p.cliente.dataFechamentoFatura >= :dataFechamento")
    BigDecimal findTotalComprasAposFechamento(@Param("clienteId") Long clienteId, @Param("dataFechamento") Date dataFechamento);
}
