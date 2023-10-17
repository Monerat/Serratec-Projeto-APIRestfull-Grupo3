package br.com.techtoy.techtoy.dto.Pedido;

import java.util.Date;

public class PedidoResponseDTO extends PedidoRequestDTO {
    
    private Long id;
    private Date dataPedido;

    
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


   

    
}
