package br.com.techtoy.techtoy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
private String dataCadastro;

@Column(nullable = false)
private String telefone;

@Column(nullable = false)
private String email;

@Column(nullable = false)
private String perfil;

@OneToMany(mappedBy = "log")
@JsonBackReference
private Log log;

@OneToMany(mappedBy = "usuario")
@JsonBackReference
private Pedido pedido;

//#endregion

//#region Getters/Setters

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

public String getDataCadastro() {
    return dataCadastro;
}

public void setDataCadastro(String dataCadastro) {
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

public String getPerfil() {
    return perfil;
}

public void setPerfil(String perfil) {
    this.perfil = perfil;
}

//#endregion

}
