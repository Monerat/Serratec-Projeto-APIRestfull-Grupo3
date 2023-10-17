package br.com.techtoy.techtoy.dto.LogDTO;

import java.util.Date;

public class LogResponseDTO {
    private long id;
    private String tipoAcao;
    private Date dataAcao;
    private long valorOriginal;
    private long valorAtual;;
    private UsuarioResponseDTO usuario; //verificar como vai ser a criação do Usuario ResponseDTO
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTipoAcao() {
        return tipoAcao;
    }
    public void setTipoAcao(String tipoAcao) {
        this.tipoAcao = tipoAcao;
    }
    public Date getDataAcao() {
        return dataAcao;
    }
    public void setDataAcao(Date dataAcao) {
        this.dataAcao = dataAcao;
    }
    public long getValorOriginal() {
        return valorOriginal;
    }
    public void setValorOriginal(long valorOriginal) {
        this.valorOriginal = valorOriginal;
    }
    public long getValorAtual() {
        return valorAtual;
    }
    public void setValorAtual(long valorAtual) {
        this.valorAtual = valorAtual;
    }
    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
    }

    
    
}
