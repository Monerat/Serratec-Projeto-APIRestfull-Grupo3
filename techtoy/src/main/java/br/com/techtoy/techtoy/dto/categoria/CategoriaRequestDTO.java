package br.com.techtoy.techtoy.dto.categoria;

import java.util.List;

import br.com.techtoy.techtoy.dto.produto.ProdutoRequestDTO;

public class CategoriaRequestDTO extends CategoriaBaseDTO {

    private List<ProdutoRequestDTO> produtos;

    public List<ProdutoRequestDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoRequestDTO> produtos) {
        this.produtos = produtos;
    }

}
