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

@Entity
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLog")
    private long id;

    @Column(nullable = false)
    private EnumLog tipoAcao;

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

    /**
     * Construtor da classe Log com parâmetros.
     * @param id Identificador único do log.
     * @param tipoAcao O tipo de ação realizada.
     * @param valorOriginal O valor original antes da ação.
     * @param valorAtual O valor após a ação.
     */
    public Log(long id, EnumLog tipoAcao, long valorOriginal, long valorAtual) {
        this.id = id;
        this.tipoAcao = tipoAcao;
        this.dataAcao = new Date();
        this.valorOriginal = valorOriginal;
        this.valorAtual = valorAtual;
    }

    /**
     * Construtor padrão da classe Log.
     * Inicializa a data da ação com a data atual.
     */
    public Log() {
        this.dataAcao = new Date();
    }

    // GETTERS AND SETTERS

    /**
     * Obtém o identificador único do log.
     * @return O identificador único do log.
     */
    public long getId() {
        return id;
    }

    /**
     * Define o identificador único do log.
     * @param id O identificador único do log.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Obtém o tipo de ação realizada no log.
     * @return O tipo de ação.
     */
    public EnumLog getTipoAcao() {
        return tipoAcao;
    }

    /**
     * Define o tipo de ação realizada no log.
     * @param tipoAcao O tipo de ação.
     */
    public void setTipoAcao(EnumLog tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    /**
     * Obtém a data da ação registrada no log.
     * @return A data da ação.
     */
    public Date getDataAcao() {
        return dataAcao;
    }

    /**
     * Define a data da ação registrada no log.
     * @param dataAcao A data da ação.
     */
    public void setDataAcao(Date dataAcao) {
        this.dataAcao = dataAcao;
    }

    /**
     * Obtém o valor original antes da ação registrada no log.
     * @return O valor original.
     */
    public long getValorOriginal() {
        return valorOriginal;
    }

    /**
     * Define o valor original antes da ação registrada no log.
     * @param valorOriginal O valor original.
     */
    public void setValorOriginal(long valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    /**
     * Obtém o valor atual após a ação registrada no log.
     * @return O valor atual.
     */
    public long getValorAtual() {
        return valorAtual;
    }

    /**
     * Define o valor atual após a ação registrada no log.
     * @param valorAtual O valor atual.
     */
    public void setValorAtual(long valorAtual) {
        this.valorAtual = valorAtual;
    }

    /**
     * Obtém o usuário associado ao log.
     * @return O usuário relacionado ao log.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define o usuário associado ao log.
     * @param usuario O usuário relacionado ao log.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}