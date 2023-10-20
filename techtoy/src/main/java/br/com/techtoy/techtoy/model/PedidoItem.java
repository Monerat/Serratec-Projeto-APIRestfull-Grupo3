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
    private int quantidade;

    private double desconto;

    private double acréscimo;

    @ManyToOne
    @JoinColumn(name = "idPedido", nullable = false)
    @JsonBackReference
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "idProduto", nullable = false)
    @JsonBackReference
    private Produto produto;

    /**
     * Construtor da classe PedidoItem com parâmetros.
     * @param id Identificador único do item de pedido.
     * @param quantidade A quantidade do item.
     * @param desconto O desconto aplicado ao item.
     * @param acréscimo O acréscimo aplicado ao item.
     */
    public PedidoItem(long id, int quantidade, double desconto, double acréscimo) {
        this.id = id;
        this.quantidade = quantidade;
        this.desconto = desconto;
        this.acréscimo = acréscimo;
    }

    /**
     * Construtor padrão da classe PedidoItem.
     */
    public PedidoItem() {
    }

    // GETTERS AND SETTERS

    /**
     * Obtém o identificador único do item de pedido.
     * @return O identificador único do item de pedido.
     */
    public long getId() {
        return id;
    }

    /**
     * Define o identificador único do item de pedido.
     * @param id O identificador único do item de pedido.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Obtém a quantidade do item de pedido.
     * @return A quantidade do item de pedido.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade do item de pedido.
     * @param quantidade A quantidade do item de pedido.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Obtém o desconto aplicado ao item de pedido.
     * @return O desconto aplicado ao item de pedido.
     */
    public double getDesconto() {
        return desconto;
    }

    /**
     * Define o desconto aplicado ao item de pedido.
     * @param desconto O desconto aplicado ao item de pedido.
     */
    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    /**
     * Obtém o acréscimo aplicado ao item de pedido.
     * @return O acréscimo aplicado ao item de pedido.
     */
    public double getAcréscimo() {
        return acréscimo;
    }

    /**
     * Define o acréscimo aplicado ao item de pedido.
     * @param acréscimo O acréscimo aplicado ao item de pedido.
     */
    public void setAcréscimo(double acréscimo) {
        this.acréscimo = acréscimo;
    }

    /**
     * Obtém o pedido ao qual este item pertence.
     * @return O pedido relacionado a este item.
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * Define o pedido ao qual este item pertence.
     * @param pedido O pedido relacionado a este item.
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    /**
     * Obtém o produto associado a este item.
     * @return O produto associado a este item.
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Define o produto associado a este item.
     * @param produto O produto associado a este item.
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}

