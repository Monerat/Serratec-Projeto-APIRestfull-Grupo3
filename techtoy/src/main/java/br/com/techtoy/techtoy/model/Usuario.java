package br.com.techtoy.techtoy.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.techtoy.techtoy.model.Enum.EnumTipoUsuario;

@Entity
@Table(name = "usuario")
public class Usuario {
    
//#region Atributos

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "idUsuario")
private long id;

@Column(nullable = false)
private String nome;

@Column(nullable = false)
private Date dataCadastro;

@Column(nullable = false)
private String telefone;

@Column(nullable = false, unique = true)
private String email;

@Column(nullable = false)
private String senha;

@Column(nullable = false)
private EnumTipoUsuario perfil;

@OneToMany(mappedBy = "usuario")
private transient List<Log> logs;

@OneToMany(mappedBy = "usuario")
private transient List<Pedido> pedidos;

public Usuario(long id, String nome, String dataCadastro, String telefone, String email, String senha, EnumTipoUsuario perfil,
        Log log, Pedido pedido) {
    this.id = id;
    this.nome = nome;
    this.telefone = telefone;
    this.email = email;
    this.senha = senha;
    this.perfil = perfil;
    this.dataCadastro = new Date();
    this.logs = new ArrayList<>();
    this.logs.add(log);
    this.pedidos = new ArrayList<>();
    this.pedidos.add(pedido);
}

public Usuario(long id) {
    this.dataCadastro = new Date();
    this.id = id;
}

public Usuario() {
    this.dataCadastro = new Date();
}

public long getId() {
    return id;
}

public void setId(long id) {
    this.id = id;
}

public String getNome() {
    return nome;
}

public void setNome(String nome) {
    this.nome = nome;
}

public Date getDataCadastro() {
    return dataCadastro;
}

public void setDataCadastro(Date dataCadastro) {
    this.dataCadastro = dataCadastro;
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

public String getSenha() {
    return senha;
}

public void setSenha(String senha) {
    this.senha = senha;
}

public EnumTipoUsuario getPerfil() {
    return perfil;
}

public void setPerfil(EnumTipoUsuario perfil) {
    this.perfil = perfil;
}

public List<Log> getLogs() {
    return logs;
}

public void setLogs(List<Log> logs) {
    this.logs = logs;
}

public List<Pedido> getPedidos() {
    return pedidos;
}

public void setPedidos(List<Pedido> pedidos) {
    this.pedidos = pedidos;
}


}