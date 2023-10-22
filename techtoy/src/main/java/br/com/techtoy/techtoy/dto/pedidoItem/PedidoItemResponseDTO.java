package br.com.techtoy.techtoy.dto.pedidoItem;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.techtoy.techtoy.dto.Pedido.PedidoResponseDTO;
import br.com.techtoy.techtoy.dto.produto.ProdutoResponseDTO;

public class PedidoItemResponseDTO extends PedidoItemBaseDTO {

    private ProdutoResponseDTO produto;
    @JsonBackReference
    private PedidoResponseDTO pedido;

    public ProdutoResponseDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoResponseDTO produto) {
        this.produto = produto;
    }

    public PedidoResponseDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoResponseDTO pedido) {
        this.pedido = pedido;
    }
}
