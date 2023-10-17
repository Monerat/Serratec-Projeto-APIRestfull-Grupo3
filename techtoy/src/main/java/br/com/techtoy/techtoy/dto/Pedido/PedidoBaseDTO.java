package br.com.techtoy.techtoy.dto.Pedido;

import java.util.Date;
import java.util.List;

import br.com.techtoy.techtoy.model.PedidoItem;
import br.com.techtoy.techtoy.model.Usuario;

public class PedidoBaseDTO {
    
    private Long id;
    private Date dataPedido;
    private int formaPagamento;
    private String observacao;
    private Usuario usuario;
    private List<PedidoItem> pedidoItens;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    public void setPedidoItens(List<PedidoItem> pedidoItens) {
        this.pedidoItens = pedidoItens;
    }

    

    
}
