package br.com.techtoy.techtoy.dto.usuario;

import java.util.List;
import br.com.techtoy.techtoy.model.Log;
import br.com.techtoy.techtoy.model.Pedido;

public abstract class UsuarioResponseDTO {

    private List<LogResponseDTO> logs;
    private List<PedidoResponseDTO> pedidos;

    public List<LogResponseDTO> getLogs() {
        return logs;
    }

    public void setLogs(List<LogResponseDTO> logs) {
        this.logs = logs;
    }

    public List<PedidoResponseDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoResponseDTO> pedidos) {
        this.pedidos = pedidos;
    }

}