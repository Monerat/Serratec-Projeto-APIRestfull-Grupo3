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

    /**
     * ID único do pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedido")
    private long id;

    /**
     * Data em que o pedido foi realizado.
     */
    @Column(nullable = false)
    private Date dataPedido;

    /**
     * Forma de pagamento do pedido.
     */
    @Column(nullable = false)
    private EnumFormaPagamento formaPagamento;

    /**
     * Valor total do pedido.
     */
    private double valorTotal;

    /**
     * Valor total do acréscimo ao pedido.
     */
    private double acrescimoTotal;

    /**
     * Valor total do desconto aplicado ao pedido.
     */
    private double descontoTotal;

    /**
     * Observações adicionais ao pedido.
     */
    private String observacao;

    /**
     * Usuário que realizou o pedido.
     */
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    /**
     * Itens associados ao pedido.
     */
    @OneToMany(mappedBy = "pedido")
    private List<PedidoItem> pedidoItens; // Transient evita que o JSON crie recursão infinita.

    /**
     * Construtor padrão para criar um novo pedido com valores padrão e data atual.
     */
    public Pedido() {
        this.dataPedido = new Date();
    }

    /**
     * Construtor para criar um novo pedido com atributos específicos.
     * 
     * @param id             ID único do pedido.
     * @param formaPagamento Forma de pagamento do pedido.
     * @param valorTotal     Valor total do pedido.
     * @param acrescimoTotal Valor total do acréscimo ao pedido.
     * @param descontoTotal  Valor total do desconto aplicado ao pedido.
     * @param observacao     Observações adicionais ao pedido.
     * @param usuario        Usuário que realizou o pedido.
     * @param pedidoItem     Item associado ao pedido.
     */
    public Pedido(long id, EnumFormaPagamento formaPagamento, double valorTotal, double acrescimoTotal,
            double descontoTotal, String observacao, Usuario usuario, PedidoItem pedidoItem) {
        this.id = id;
        this.dataPedido = new Date();
        this.formaPagamento = formaPagamento;
        this.valorTotal = valorTotal;
        this.acrescimoTotal = acrescimoTotal;
        this.descontoTotal = descontoTotal;
        this.observacao = observacao;
        this.usuario = usuario;
        this.pedidoItens = new ArrayList<>();
        this.pedidoItens.add(pedidoItem);
    }

    // GETTERS E SETTERS

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public EnumFormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(EnumFormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getAcrescimoTotal() {
        return acrescimoTotal;
    }

    public void setAcrescimoTotal(double acrescimoTotal) {
        this.acrescimoTotal = acrescimoTotal;
    }

    public double getDescontoTotal() {
        return descontoTotal;
    }

    public void setDescontoTotal(double descontoTotal) {
        this.descontoTotal = descontoTotal;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<PedidoItem> getPedidoItens() {
        return pedidoItens;
    }

    public void setPedidoItens(List<PedidoItem> pedidoItens) {
        this.pedidoItens = pedidoItens;
    }

}
