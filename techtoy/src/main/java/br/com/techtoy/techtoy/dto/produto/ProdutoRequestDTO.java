package br.com.techtoy.techtoy.dto.produto;

import java.util.List;

import br.com.techtoy.techtoy.dto.categoria.CategoriaRequestDTO;
import br.com.techtoy.techtoy.dto.pedidoItem.PedidoItemRequestDTO;

public class ProdutoRequestDTO extends ProdutoBaseDTO {

    private CategoriaRequestDTO categoria;
    private List<PedidoItemRequestDTO> pedidoItens;

    public CategoriaRequestDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaRequestDTO categoria) {
        this.categoria = categoria;
    }

    public List<PedidoItemRequestDTO> getPedidoItens() {
        return pedidoItens;
    }

    public void setPedidoItens(List<PedidoItemRequestDTO> pedidoItens) {
        this.pedidoItens = pedidoItens;
    }

}
