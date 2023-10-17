package br.com.techtoy.techtoy.dto.produto;

import java.util.List;

import br.com.techtoy.techtoy.dto.categoria.CategoriaResponseDTO;
import br.com.techtoy.techtoy.dto.pedidoItem.PedidoItemResponseDTO;

public class ProdutoResponseDTO extends ProdutoBaseDTO{
    
    private CategoriaResponseDTO categoria;
    private List<PedidoItemResponseDTO> pedidoItens;

    public CategoriaResponseDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaResponseDTO categoria) {
        this.categoria = categoria;
    }

    public List<PedidoItemResponseDTO> getPedidoItens() {
        return pedidoItens;
    }

    public void setPedidoItens(List<PedidoItemResponseDTO> pedidoItens) {
        this.pedidoItens = pedidoItens;
    }
}