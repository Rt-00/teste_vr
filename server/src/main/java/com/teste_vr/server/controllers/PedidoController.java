package com.teste_vr.server.controllers;

import com.teste_vr.server.dtos.pedido.CreatePedidoDTO;
import com.teste_vr.server.dtos.pedido.ListPedidoDTO;
import com.teste_vr.server.dtos.pedido.UpdatePedidoDTO;
import com.teste_vr.server.services.pedido.PedidoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * Lista todos os Pedidos.
     *
     * @return Uma {@link List} de {@link ListPedidoDTO}.
     */
    @GetMapping
    public ResponseEntity<List<ListPedidoDTO>> listarProdutos() {
        List<ListPedidoDTO> cliente = pedidoService.listarProdutos();
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    /**
     * Deleta um Pedido pelo seu Código.
     *
     * @param codigo O Código do Pedido a ser deletado.
     * @return Uma resposta vazia indicando sucesso.
     */
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long codigo) {
        pedidoService.excluirProduto(codigo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<ListPedidoDTO> salvarPedido(@RequestBody CreatePedidoDTO createPedidoDTO) {
        ListPedidoDTO pedido = pedidoService.salvarPedido(createPedidoDTO);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ListPedidoDTO> atualizarProduto(@PathVariable Long codigo,
                                                          @RequestBody UpdatePedidoDTO updatePedidoDTO) {
        ListPedidoDTO pedidoAtualizado = pedidoService.atualizarPedido(updatePedidoDTO, codigo);
        return new ResponseEntity<>(pedidoAtualizado, HttpStatus.OK);
    }
}
