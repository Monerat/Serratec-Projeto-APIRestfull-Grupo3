package br.com.techtoy.techtoy.model;


import java.util.List;
import java.util.ArrayList;
import java.util.Date;

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

import br.com.techtoy.techtoy.model.Enum.EnumFormaPagamento;





@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedido")
    private long id;

    @Column(nullable = false)
    private Date dataPedido;

    @Column(nullable = false)
    private EnumFormaPagamento formaPagamento;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido")
    private List<PedidoItem> pedidoItens;

    /**
     * Construtor da classe Pedido com parâmetros.
     * @param id Identificador único do pedido.
     * @param formaPagamento A forma de pagamento do pedido.
     * @param observacao Observações sobre o pedido.
     * @param usuario O usuário que fez o pedido.
     * @param pedidoItem O item do pedido a ser adicionado.
     */
    public Pedido(long id, EnumFormaPagamento formaPagamento, String observacao, Usuario usuario, PedidoItem pedidoItem) {
        this.id = id;
        this.dataPedido = new Date();
        this.formaPagamento = formaPagamento;
        this.observacao = observacao;
        this.usuario = usuario;
        this.pedidoItens = new ArrayList<>();
        this.pedidoItens.add(pedidoItem);
    }

    /**
     * Construtor da classe Pedido com parâmetros.
     * @param id Identificador único do pedido.
     */
    public Pedido(long id) {
        this.id = id;
        this.dataPedido = new Date();
    }

    /**
     * Construtor padrão da classe Pedido.
     * Inicializa a data do pedido com a data atual.
     */
    public Pedido() {
        this.dataPedido = new Date();
    }

    // GETTERS AND SETTERS

    /**
     * Obtém o identificador único do pedido.
     * @return O identificador único do pedido.
     */
    public long getId() {
        return id;
    }

    /**
     * Define o identificador único do pedido.
     * @param id O identificador único do pedido.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Obtém a data do pedido.
     * @return A data do pedido.
     */
    public Date getDataPedido() {
        return dataPedido;
    }

    /**
     * Define a data do pedido.
     * @param dataPedido A data do pedido.
     */
    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    /**
     * Obtém a forma de pagamento do pedido.
     * @return A forma de pagamento do pedido.
     */
    public EnumFormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    /**
     * Define a forma de pagamento do pedido.
     * @param formaPagamento A forma de pagamento do pedido.
     */
    public void setFormaPagamento(EnumFormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    /**
     * Obtém as observações sobre o pedido.
     * @return As observações sobre o pedido.
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * Define as observações sobre o pedido.
     * @param observacao As observações sobre o pedido.
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    /**
     * Obtém o usuário que fez o pedido.
     * @return O usuário que fez o pedido.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define o usuário que fez o pedido.
     * @param usuario O usuário que fez o pedido.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtém a lista de itens do pedido.
     * @return A lista de itens do pedido.
     */
    public List<PedidoItem> getPedidoItens() {
        return pedidoItens;
    }

    /**
     * Define a lista de itens do pedido.
     * @param pedidoItem A lista de itens do pedido.
     */
    public void setPedidoItens(List<PedidoItem> pedidoItem) {
        this.pedidoItens = pedidoItem;
    }
}

