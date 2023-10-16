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
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoria")
    private long id;

    @Column(nullable = false)
    private String nome;

    private String observacao;

    @Column(nullable = false)
    private boolean ativo;

    @OneToMany(mappedBy = "categoria")
    private List<Produto> produtos;

    
    public Categoria(long id, String nome, String observacao, boolean ativo, Produto produto) {
        this.id = id;
        this.nome = nome;
        this.observacao = observacao;
        this.ativo = ativo;
        this.produtos = new ArrayList<>();
        this.produtos.add(produto);
    }

    public Categoria(long id) {
        this.id = id;
    }

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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

   
}
