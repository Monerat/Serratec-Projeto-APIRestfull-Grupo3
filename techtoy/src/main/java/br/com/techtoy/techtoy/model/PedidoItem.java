package br.com.techtoy.techtoy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    private int quantidade;

    private double desconto;

    private double acrescimo;

    @ManyToOne
    @JoinColumn(name = "idPedido", nullable = false) // Aqui vai gerar no banco uma coluna na tabela contaBancaria com o id_titular
    @JsonBackReference
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "idProduto", nullable = false) // Aqui vai gerar no banco uma coluna na tabela contaBancaria com o id_titular
    @JsonBackReference
    private Produto produto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(double acrescimo) {
        this.acrescimo = acrescimo;
    }

}
