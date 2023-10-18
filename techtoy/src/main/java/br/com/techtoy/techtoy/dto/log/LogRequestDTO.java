package br.com.techtoy.techtoy.dto.log;

import br.com.techtoy.techtoy.dto.usuario.UsuarioRequestDTO;
import br.com.techtoy.techtoy.model.Enum.EnumLog;

public class LogRequestDTO {
    private EnumLog tipoAcao;
    private long valorOriginal;
    private long valorAtual;
    private UsuarioRequestDTO usuario; //verificar como vai ser a criação do Usuario ResponseDTO

    //não colocar id pois é gerado automaticamente e dataAcao vai ser gerado junto

    public EnumLog getTipoAcao() {
        return tipoAcao;
    }
    public void setTipoAcao(EnumLog tipoAcao) {
        this.tipoAcao = tipoAcao;
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
    public UsuarioRequestDTO getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioRequestDTO usuario) {
        this.usuario = usuario;
    }


    
}
