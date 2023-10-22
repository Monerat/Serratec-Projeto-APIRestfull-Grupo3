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
    /**
     * ID único do item de pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedidoItem")
    private long id;

    /**
     * Quantidade do produto no item de pedido.
     */
    @Column(nullable = false)
    private Integer quantidade;

    /**
     * Valor do desconto aplicado ao item de pedido.
     */
    private Double desconto;

    /**
     * Subtotal do item de pedido (calculado automaticamente).
     */
    private Double subTotal;

    /**
     * Valor do acréscimo aplicado ao item de pedido.
     */
    private Double acrescimo;

    /**
     * Pedido ao qual este item de pedido está associado.
     */
    @ManyToOne
    @JoinColumn(name = "idPedido", nullable = false)
    @JsonBackReference
    private Pedido pedido;

    /**
     * Produto associado a este item de pedido.
     */
    @ManyToOne
    @JoinColumn(name = "idProduto", nullable = false)
    @JsonBackReference
    private Produto produto;

    /**
     * Construtor para criar um novo item de pedido com atributos específicos.
     * 
     * @param id         ID único do item de pedido.
     * @param quantidade Quantidade do produto no item de pedido.
     * @param desconto   Valor do desconto aplicado ao item de pedido.
     * @param acrescimo  Valor do acréscimo aplicado ao item de pedido.
     */
    public PedidoItem(long id, int quantidade, double desconto, double acrescimo) {
        this.id = id;
        this.quantidade = quantidade;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
    }

    /**
     * Construtor padrão para criar um novo item de pedido.
     */
    public PedidoItem() {
    }

    // GETTERS E SETTERS

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

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
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
