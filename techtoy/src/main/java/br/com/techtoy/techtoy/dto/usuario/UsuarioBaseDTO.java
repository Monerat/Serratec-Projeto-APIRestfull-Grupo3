package br.com.techtoy.techtoy.dto.usuario;

import br.com.techtoy.techtoy.model.Enum.EnumTipoUsuario;

public abstract class UsuarioBaseDTO {

    protected long id;
    protected String nome;
    protected String telefone;
    protected String email;
    protected EnumTipoUsuario perfil;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnumTipoUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(EnumTipoUsuario perfil) {
        this.perfil = perfil;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}