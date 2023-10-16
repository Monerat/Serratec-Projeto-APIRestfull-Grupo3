package br.com.techtoy.techtoy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "log")
public class Log {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLog")
    private long id;

    @Column(nullable = false)
    //verificar posteriormente se iremos utilizar enum
    private String tipoAcao;

    @Column(nullable = false)
    private Date dataAcao;

    @Column(nullable = false)
    private long valorOriginal;

    @Column(nullable = false)
    private long valorAtual;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    
    public Log(long id, String tipoAcao, long valorOriginal, long valorAtual) {
        this.id = id;
        this.tipoAcao = tipoAcao;
        this.dataAcao = new Date();
        this.valorOriginal = valorOriginal;
        this.valorAtual = valorAtual;
    }

    public Log() {
        this.dataAcao = new Date();
    }

    // GETTERS AND SETTERS
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
