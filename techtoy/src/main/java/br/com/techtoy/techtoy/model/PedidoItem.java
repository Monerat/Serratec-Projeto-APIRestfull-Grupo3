package br.com.techtoy.techtoy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "pedidoItem")
public class PedidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedidoItem")
    private long id;

    @Column(nullable = false)
    private Integer quantidade;

    private Double desconto;

    private Double acrescimo;

    @ManyToOne
    @JoinColumn(name = "idPedido", nullable = false) // Aqui vai gerar no banco uma coluna na tabela contaBancaria com o id_titular
    @JsonBackReference
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "idProduto", nullable = false) // Aqui vai gerar no banco uma coluna na tabela contaBancaria com o id_titular
    @JsonBackReference
    private Produto produto;


    
    public PedidoItem(long id, int quantidade, double desconto, double acrescimo) {
        this.id = id;
        this.quantidade = quantidade;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
    }
    
    public PedidoItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(Double acrescimo) {
        this.acrescimo = acrescimo;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    
    
}
