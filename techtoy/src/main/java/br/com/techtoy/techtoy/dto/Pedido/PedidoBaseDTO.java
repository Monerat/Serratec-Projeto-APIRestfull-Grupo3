package br.com.techtoy.techtoy.dto.Pedido;

public class PedidoBaseDTO {
    
    protected Long id;
    protected int formaPagamento;
    protected String observacao;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    
}
