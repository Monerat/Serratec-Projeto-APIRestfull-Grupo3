package br.com.techtoy.techtoy.dto.usuario;

import java.util.Date;

public class UsuarioResponseCreateDTO extends UsuarioBaseDTO {

    private Date dataCadastro;

    public UsuarioResponseCreateDTO() {
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

}