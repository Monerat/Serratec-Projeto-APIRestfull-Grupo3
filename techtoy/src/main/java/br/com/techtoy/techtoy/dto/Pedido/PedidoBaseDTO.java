package br.com.techtoy.techtoy.dto.Pedido;

import java.util.Date;

import br.com.techtoy.techtoy.model.Enum.EnumFormaPagamento;

public class PedidoBaseDTO {
    
    protected Long id;
    protected Date dataPedido;
    protected EnumFormaPagamento formaPagamento;
    protected String observacao;

    
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
    public EnumFormaPagamento getFormaPagamento() {
        return formaPagamento;
    }
    public void setFormaPagamento(EnumFormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    
    
    
}
