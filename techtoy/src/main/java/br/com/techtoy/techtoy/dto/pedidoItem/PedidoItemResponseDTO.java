package br.com.techtoy.techtoy.dto.pedidoItem;

import br.com.techtoy.techtoy.dto.produto.ProdutoResponseDTO;

public class PedidoItemResponseDTO  extends PedidoItemBaseDTO{
    
    private ProdutoResponseDTO produto;
    private PedidoItemResponseDTO pedido;

    public ProdutoResponseDTO getProduto() {
        return produto;
    }
    public void setProduto(ProdutoResponseDTO produto) {
        this.produto = produto;
    }
    public PedidoItemResponseDTO getPedido() {
        return pedido;
    }
    public void setPedido(PedidoItemResponseDTO pedido) {
        this.pedido = pedido;
    }   
}
