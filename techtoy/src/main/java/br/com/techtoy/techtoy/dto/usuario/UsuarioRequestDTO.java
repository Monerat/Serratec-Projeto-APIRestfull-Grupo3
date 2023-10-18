package br.com.techtoy.techtoy.dto.usuario;

import java.util.List;

import br.com.techtoy.techtoy.dto.Pedido.PedidoRequestDTO;
import br.com.techtoy.techtoy.dto.log.LogRequestDTO;

public class UsuarioRequestDTO extends UsuarioBaseDTO {

    private List<LogRequestDTO> logs;
    private List<PedidoRequestDTO> pedidos;
    private String senha;

    public UsuarioRequestDTO() {
    }

    public UsuarioRequestDTO(List<LogRequestDTO> logs, List<PedidoRequestDTO> pedidos) {
        this.logs = logs;
        this.pedidos = pedidos;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

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