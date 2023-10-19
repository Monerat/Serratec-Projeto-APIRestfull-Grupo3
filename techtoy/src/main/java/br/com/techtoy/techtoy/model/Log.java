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

import br.com.techtoy.techtoy.model.Enum.EnumLog;
import br.com.techtoy.techtoy.model.Enum.EnumTipoEntidade;

@Entity
@Table(name = "log")
public class Log {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLog")
    private long id;

    @Column(nullable = false)
    //verificar posteriormente se iremos utilizar enum
    private EnumLog tipoAcao;

    @Column(nullable = false)
    private EnumTipoEntidade tipoEntidade;

    @Column(nullable = false)
    private Date dataAcao;

    private String valorOriginal;

    private String valorAtual;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    
    public Log(long id, EnumLog tipoAcao, EnumTipoEntidade tipoEntidade, String valorOriginal, String valorAtual) {
        this.id = id;
        this.tipoAcao = tipoAcao;
        this.tipoEntidade = tipoEntidade;
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

    public EnumLog getTipoAcao() {
        return tipoAcao;
    }

    public void setTipoAcao(EnumLog tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    public Date getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(Date dataAcao) {
        this.dataAcao = dataAcao;
    }

    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
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
