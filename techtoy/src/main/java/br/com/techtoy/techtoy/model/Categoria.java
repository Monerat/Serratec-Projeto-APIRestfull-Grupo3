/**
 * A classe Categoria representa uma categoria de produtos no sistema, com informações como nome, observação, estado de ativação
 * e a lista de produtos associados a essa categoria.
 */
package br.com.techtoy.techtoy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria {

    // Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoria")
    private long id;

    @Column(nullable = false, unique = true)
    private String nome;

    private String observacao;

    @Column(nullable = false)
    private Boolean ativo;

    @OneToMany(mappedBy = "categoria")
    private List<Produto> produtos;

    /**
     * Construtor que inicializa um objeto de Categoria com os parâmetros
     * fornecidos.
     *
     * @param id         O ID da categoria.
     * @param nome       O nome da categoria.
     * @param observacao Uma observação sobre a categoria.
     * @param ativo      O estado de ativação da categoria.
     * @param produto    O produto associado à categoria.
     */
    public Categoria(long id, String nome, String observacao, boolean ativo, Produto produto) {
        this.id = id;
        this.nome = nome;
        this.observacao = observacao;
        this.ativo = ativo;
        this.produtos = new ArrayList<>();
        this.produtos.add(produto);
    }

    /**
     * Construtor que inicializa um objeto de Categoria com um ID específico.
     *
     * @param id O ID da categoria.
     */
    public Categoria(long id) {
        this.id = id;
    }

    /**
     * Construtor que inicializa um objeto de Categoria com valores padrão.
     */

    // Getters e Setters
    public Categoria() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

}
