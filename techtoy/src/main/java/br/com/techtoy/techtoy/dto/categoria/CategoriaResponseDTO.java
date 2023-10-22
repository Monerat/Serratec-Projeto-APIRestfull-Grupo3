package br.com.techtoy.techtoy.dto.categoria;

import java.util.List;

import br.com.techtoy.techtoy.dto.produto.ProdutoResponseDTO;

public class CategoriaResponseDTO extends CategoriaBaseDTO {

    private List<ProdutoResponseDTO> produtos;

    public List<ProdutoResponseDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoResponseDTO> produtos) {
        this.produtos = produtos;
    }

}
