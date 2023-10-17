package br.com.techtoy.techtoy.dto.log;

import br.com.techtoy.techtoy.dto.usuario.UsuarioRequestDTO;

public class LogRequestDTO {
    private String tipoAcao;
    private long valorOriginal;
    private long valorAtual;
    private UsuarioRequestDTO usuario; //verificar como vai ser a criação do Usuario ResponseDTO

    //não colocar id pois é gerado automaticamente e dataAcao vai ser gerado junto

    public String getTipoAcao() {
        return tipoAcao;
    }
    public void setTipoAcao(String tipoAcao) {
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
