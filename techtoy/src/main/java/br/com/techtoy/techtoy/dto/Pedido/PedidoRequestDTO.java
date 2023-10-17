package br.com.techtoy.techtoy.dto.Pedido;

import java.util.List;

import br.com.techtoy.techtoy.model.PedidoItem;


public class PedidoRequestDTO {
    
   
    private int formaPagamento;
    private String observacao;
    private Long idUsuario;
    private List<PedidoItem> pedidoItens;

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
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    public List<PedidoItem> getPedidoItens() {
        return pedidoItens;
    }
    public void setPedidoItens(List<PedidoItem> pedidoItens) {
        this.pedidoItens = pedidoItens;
    }

    
}
