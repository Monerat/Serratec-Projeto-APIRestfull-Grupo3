package br.com.techtoy.techtoy.model.email;

import java.util.List;

public class Email {

    private String assunto;
    private String mensagem;
    private String remetente;
    private List<String> destinatarios;

    /**
     * Construtor para criar um objeto de e-mail com atributos específicos.
     * 
     * @param assunto       O assunto do e-mail.
     * @param mensagem      O conteúdo da mensagem do e-mail.
     * @param destinatarios A lista de destinatários do e-mail.
     */
    public Email(String assunto, String mensagem, List<String> destinatarios) {
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.remetente = "grupo3.serratec.apirestful23.2@gmail.com";
        this.destinatarios = destinatarios;
    }

    /**
     * Construtor vazio para criar um objeto de e-mail sem atributos iniciais.
     */
    public Email() {
    }

    // GETTERS E SETTERS

    /**
     * Obtém o assunto do e-mail.
     * 
     * @return O assunto do e-mail.
     */
    public String getAssunto() {
        return assunto;
    }

    /**
     * Define o assunto do e-mail.
     * 
     * @param assunto O assunto a ser definido.
     */
    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    /**
     * Obtém a mensagem do e-mail.
     * 
     * @return A mensagem do e-mail.
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Define a mensagem do e-mail.
     * 
     * @param mensagem A mensagem a ser definida.
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * Obtém o remetente do e-mail.
     * 
     * @return O remetente do e-mail.
     */
    public String getRemetente() {
        return remetente;
    }

    /**
     * Define o remetente do e-mail.
     * 
     * @param remetente O remetente a ser definido.
     */
    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    /**
     * Obtém a lista de destinatários do e-mail.
     * 
     * @return A lista de destinatários.
     */
    public List<String> getDestinatarios() {
        return destinatarios;
    }

    /**
     * Define a lista de destinatários do e-mail.
     * 
     * @param destinatarios A lista de destinatários a ser definida.
     */
    public void setDestinatarios(List<String> destinatarios) {
        this.destinatarios = destinatarios;
    }
}
