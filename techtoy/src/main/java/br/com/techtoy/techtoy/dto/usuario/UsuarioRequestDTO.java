package br.com.techtoy.techtoy.dto.usuario;

import java.util.List;
import br.com.techtoy.techtoy.model.Log;
import br.com.techtoy.techtoy.model.Pedido;

public abstract class UsuarioRequestDTO extends UsuarioBaseDTO {

    private List<LogRequestDTO> logs;
    private List<PedidoRequestDTO> pedidos;

    public List<LogRequestDTO> getLogs() {
        return logs;
    }

    public void setLogs(List<LogRequestDTO> logs) {
        this.logs = logs;
    }

    public List<PedidoRequestDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoRequestDTO> pedidos) {
        this.pedidos = pedidos;
    }

}