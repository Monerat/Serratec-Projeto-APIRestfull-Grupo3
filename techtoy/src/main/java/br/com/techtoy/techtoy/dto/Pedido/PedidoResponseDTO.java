package br.com.techtoy.techtoy.dto.Pedido;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.techtoy.techtoy.dto.pedidoItem.PedidoItemResponseDTO;
import br.com.techtoy.techtoy.dto.usuario.UsuarioResponseDTO;

public class PedidoResponseDTO extends PedidoBaseDTO {
    @JsonBackReference
    private UsuarioResponseDTO usuario;

    private Date dataPedido;

    private List<PedidoItemResponseDTO> pedidoItens;

    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
    }

    public List<PedidoItemResponseDTO> getPedidoItens() {
        return pedidoItens;
    }

    public void setPedidoItens(List<PedidoItemResponseDTO> pedidoItens) {
        this.pedidoItens = pedidoItens;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

}