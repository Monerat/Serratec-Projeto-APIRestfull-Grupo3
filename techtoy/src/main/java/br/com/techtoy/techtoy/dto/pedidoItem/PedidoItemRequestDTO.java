package br.com.techtoy.techtoy.dto.pedidoItem;

import br.com.techtoy.techtoy.dto.produto.ProdutoRequestDTO;

public class PedidoItemRequestDTO extends PedidoItemBaseDTO {
    
    private ProdutoRequestDTO produto;
    private PedidoItemRequestDTO pedido;
    
    public ProdutoRequestDTO getProduto() {
        return produto;
    }
    public void setProduto(ProdutoRequestDTO produto) {
        this.produto = produto;
    }
    public PedidoItemRequestDTO getPedido() {
        return pedido;
    }
    public void setPedido(PedidoItemRequestDTO pedido) {
        this.pedido = pedido;
    }
    
}
