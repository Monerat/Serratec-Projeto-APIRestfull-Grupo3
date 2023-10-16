package br.com.techtoy.techtoy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduto")
    private long id;

    @Column(nullable = false)
    private String nome;

    private String observacao;

    @Column(nullable = false)
    private int estoque;

    @Column(nullable = false)
    private Double valorUn;

    @Column(nullable = false)
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "idCategoria", nullable = false)
    @JsonBackReference
    private Categoria categoria;

    @OneToMany(mappedBy = "produto")
    private List<PedidoItem> pedidoItens;

    

    public Produto(long id, String nome, String observacao, int estoque, Double valorUn, boolean ativo,
            PedidoItem pedidoItem) {
        this.id = id;
        this.nome = nome;
        this.observacao = observacao;
        this.estoque = estoque;
        this.valorUn = valorUn;
        this.ativo = ativo;
        this.pedidoItens = new ArrayList<>();
        this.pedidoItens.add(pedidoItem);
    }

    public Produto(long id) {
        this.id = id;
    }

    public Produto() {
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

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public Double getValorUn() {
        return valorUn;
    }

    public void setValorUn(Double valorUn) {
        this.valorUn = valorUn;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<PedidoItem> getPedidoItens() {
        return pedidoItens;
    }

    public void setPedidoItens(List<PedidoItem> pedidoItens) {
        this.pedidoItens = pedidoItens;
    }
    
    
   
}
