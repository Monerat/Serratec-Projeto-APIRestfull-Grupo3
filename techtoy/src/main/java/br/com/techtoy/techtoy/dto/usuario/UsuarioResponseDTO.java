package br.com.techtoy.techtoy.dto.usuario;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.techtoy.techtoy.dto.Pedido.PedidoResponseDTO;
import br.com.techtoy.techtoy.dto.log.LogResponseDTO;

public class UsuarioResponseDTO extends UsuarioBaseDTO {

    @JsonBackReference
    private List<LogResponseDTO> logs;
    
    private List<PedidoResponseDTO> pedidos;
    private Date dataCadastro;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(List<LogResponseDTO> logs, List<PedidoResponseDTO> pedidos) {
        this.logs = logs;
        this.pedidos = pedidos;
    }

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

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

}