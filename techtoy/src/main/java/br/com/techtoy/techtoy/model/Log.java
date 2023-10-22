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

    /**
     * ID único do log.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLog")
    private long id;

    /**
     * Tipo de ação realizada, como criar, atualizar ou excluir (usando EnumLog).
     */
    @Column(nullable = false)
    private EnumLog tipoAcao;

    /**
     * Tipo de entidade afetada pela ação (usando EnumTipoEntidade).
     */
    @Column(nullable = false)
    private EnumTipoEntidade tipoEntidade;

    /**
     * Data e hora em que o log foi criado.
     */
    @Column(nullable = false)
    private Date dataAcao;

    /**
     * Valor original associado à ação .
     */
    @Column(columnDefinition = "text")
    private String valorOriginal;

    /**
     * Valor atual associado à ação .
     */
    @Column(columnDefinition = "text")
    private String valorAtual;

    /**
     * Usuário relacionado ao log.
     */
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    /**
     * Construtor para criar um novo log com atributos específicos.
     * 
     * @param id            ID único do log.
     * @param tipoAcao      Tipo de ação realizada.
     * @param tipoEntidade  Tipo de entidade afetada pela ação.
     * @param valorOriginal Valor original associado à ação.
     * @param valorAtual    Valor atual associado à ação.
     */
    public Log(long id, EnumLog tipoAcao, EnumTipoEntidade tipoEntidade, String valorOriginal, String valorAtual) {
        this.id = id;
        this.tipoAcao = tipoAcao;
        this.tipoEntidade = tipoEntidade;
        this.dataAcao = new Date();
        this.valorOriginal = valorOriginal;
        this.valorAtual = valorAtual;
    }

    /**
     * Construtor padrão para criar um novo log com valores padrão e data atual.
     */
    public Log() {
        this.dataAcao = new Date();
    }

    // GETTERS E SETTERS

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
