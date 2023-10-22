package br.com.techtoy.techtoy.dto.produto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.techtoy.techtoy.dto.categoria.CategoriaResponseDTO;

public class ProdutoResponseDTO extends ProdutoBaseDTO {
    @JsonBackReference
    private CategoriaResponseDTO categoria;

    public CategoriaResponseDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaResponseDTO categoria) {
        this.categoria = categoria;
    }
}