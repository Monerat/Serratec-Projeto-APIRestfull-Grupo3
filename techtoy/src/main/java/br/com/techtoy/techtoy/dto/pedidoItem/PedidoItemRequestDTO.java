package br.com.techtoy.techtoy.dto.pedidoItem;

import br.com.techtoy.techtoy.dto.Pedido.PedidoRequestDTO;
import br.com.techtoy.techtoy.dto.produto.ProdutoRequestDTO;

public class PedidoItemRequestDTO extends PedidoItemBaseDTO {

    private ProdutoRequestDTO produto;
    private PedidoRequestDTO pedido;

    public ProdutoRequestDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoRequestDTO produto) {
        this.produto = produto;
    }

    public PedidoRequestDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoRequestDTO pedido) {
        this.pedido = pedido;
    }

}
