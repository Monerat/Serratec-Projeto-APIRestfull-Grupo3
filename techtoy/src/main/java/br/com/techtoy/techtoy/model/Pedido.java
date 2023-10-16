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
    private int formaPagamento;
   
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido")
    private List<PedidoItem> pedidoItens;

    
    
    public Pedido(long id, int formaPagamento, String observacao, Usuario usuario,
            PedidoItem pedidoItem) {
        this.id = id;
        this.dataPedido = new Date();
        this.formaPagamento = formaPagamento;
        this.observacao = observacao;
        this.usuario = usuario;
        this.pedidoItens = new ArrayList<>();
        this.pedidoItens.add(pedidoItem);
    } 
    
    
    public Pedido(long id) {
        this.id = id;
        this.dataPedido = new Date();
    }


    public Pedido() {
        this.dataPedido = new Date();
    }


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

    public int getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(int formaPagamento) {
        this.formaPagamento = formaPagamento;
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

    public void setPedidoItens(List<PedidoItem> pedidoItem) {
        this.pedidoItens = pedidoItem;
    }



 
}
