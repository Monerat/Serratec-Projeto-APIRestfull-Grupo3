/**
 * A classe Produto representa um item disponível para compra no sistema, com informações como nome, observação, estoque,
 * valor unitário, estado de ativação e relação com a categoria de produtos.
 */
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

    // Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduto")
    private long id;

    @Column(nullable = false)
    private String nome;

    private String observacao;

    @Column(nullable = false)
    private Integer estoque;

    @Column(nullable = false)
    private Double valorUn;

    @Column(nullable = false)
    private Boolean ativo;

    private String imagem;

    @ManyToOne
    @JoinColumn(name = "idCategoria", nullable = false)
    @JsonBackReference
    private Categoria categoria;

    @OneToMany(mappedBy = "produto")
    private List<PedidoItem> pedidoItens;

    /**
     * Construtor que inicializa um objeto de Produto com os parâmetros fornecidos.
     *
     * @param id         O ID do produto.
     * @param nome       O nome do produto.
     * @param observacao Uma observação sobre o produto.
     * @param estoque    A quantidade em estoque do produto.
     * @param valorUn    O valor unitário do produto.
     * @param ativo      O estado de ativação do produto.
     * @param pedidoItem O item de pedido associado ao produto.
     */
    public Produto(long id, String nome, String observacao, int estoque, Double valorUn, Boolean ativo, String imagem,
            PedidoItem pedidoItem) {
        this.id = id;
        this.nome = nome;
        this.observacao = observacao;
        this.estoque = estoque;
        this.valorUn = valorUn;
        this.ativo = ativo;
        this.imagem = imagem;
        this.pedidoItens = new ArrayList<>();
        this.pedidoItens.add(pedidoItem);
    }

    /**
     * Construtor que inicializa um objeto de Produto com um ID específico.
     *
     * @param id O ID do produto.
     */
    public Produto(long id) {
        this.id = id;
    }

    /**
     * Construtor que inicializa um objeto de Produto com valores padrão.
     */
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

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Double getValorUn() {
        return valorUn;
    }

    public void setValorUn(Double valorUn) {
        this.valorUn = valorUn;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
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

    // Getters e Setters

}
