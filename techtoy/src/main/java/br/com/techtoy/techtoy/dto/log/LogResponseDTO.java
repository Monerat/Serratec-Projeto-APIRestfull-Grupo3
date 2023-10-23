package br.com.techtoy.techtoy.dto.log;

import java.util.Date;

import br.com.techtoy.techtoy.dto.usuario.UsuarioResponseDTO;
import br.com.techtoy.techtoy.model.Enum.EnumLog;
import br.com.techtoy.techtoy.model.Enum.EnumTipoEntidade;

public class LogResponseDTO {

    private long id;
    private EnumLog tipoAcao;
    private EnumTipoEntidade tipoEntidade;
    private Date dataAcao;
    private String valorOriginal;
    private String valorAtual;

    private UsuarioResponseDTO usuario; // verificar como vai ser a criação do Usuario ResponseDTO

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(Date dataAcao) {
        this.dataAcao = dataAcao;
    }

    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
    }

    public EnumLog getTipoAcao() {
        return tipoAcao;
    }

    public void setTipoAcao(EnumLog tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    public EnumTipoEntidade getTipoEntidade() {
        return tipoEntidade;
    }

    public void setTipoEntidade(EnumTipoEntidade tipoEntidade) {
        this.tipoEntidade = tipoEntidade;
    }

    public String getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(String valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public String getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(String valorAtual) {
        this.valorAtual = valorAtual;
    }

}
