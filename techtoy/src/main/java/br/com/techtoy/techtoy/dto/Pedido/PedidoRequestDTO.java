package br.com.techtoy.techtoy.dto.Pedido;

import java.util.List;

import br.com.techtoy.techtoy.dto.pedidoItem.PedidoItemRequestDTO;
import br.com.techtoy.techtoy.dto.usuario.UsuarioRequestDTO;

public class PedidoRequestDTO extends PedidoBaseDTO {

    private UsuarioRequestDTO usuario;
    private List<PedidoItemRequestDTO> pedidoItens;

    public UsuarioRequestDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioRequestDTO usuario) {
        this.usuario = usuario;
    }

    public List<PedidoItemRequestDTO> getPedidoItens() {
        return pedidoItens;
    }

    public void setPedidoItens(List<PedidoItemRequestDTO> pedidoItens) {
        this.pedidoItens = pedidoItens;
    }
}
