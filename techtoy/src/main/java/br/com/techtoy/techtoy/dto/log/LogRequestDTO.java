package br.com.techtoy.techtoy.dto.log;

import br.com.techtoy.techtoy.dto.usuario.UsuarioRequestDTO;
import br.com.techtoy.techtoy.model.Enum.EnumLog;
import br.com.techtoy.techtoy.model.Enum.EnumTipoEntidade;

public class LogRequestDTO {
    private EnumLog tipoAcao;
    private EnumTipoEntidade tipoEntidade;
    private String valorOriginal;
    private String valorAtual;
    private UsuarioRequestDTO usuario; // verificar como vai ser a criação do Usuario ResponseDTO

    // não colocar id pois é gerado automaticamente e dataAcao vai ser gerado junto

    public LogRequestDTO(EnumLog tipoAcao, EnumTipoEntidade tipoEntidade, String valorOriginal, String valorAtual,
            UsuarioRequestDTO usuario) {
        this.tipoAcao = tipoAcao;
        this.tipoEntidade = tipoEntidade;
        this.valorOriginal = valorOriginal;
        this.valorAtual = valorAtual;
        this.usuario = usuario;
    }

    public LogRequestDTO() {
    }

    public EnumLog getTipoAcao() {
        return tipoAcao;
    }

    public void setTipoAcao(EnumLog tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    public UsuarioRequestDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioRequestDTO usuario) {
        this.usuario = usuario;
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
