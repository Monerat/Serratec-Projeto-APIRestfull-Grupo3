package br.com.techtoy.techtoy.dto.categoria;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.techtoy.techtoy.dto.produto.ProdutoResponseDTO;
public class CategoriaResponseDTO extends CategoriaBaseDTO{
    @JsonBackReference
    private List<ProdutoResponseDTO> produtos;

    public List<ProdutoResponseDTO> getProdutos() {
        return produtos;
    }
    
    public void setProdutos(List<ProdutoResponseDTO> produtos) {
        this.produtos = produtos;
    }

}
