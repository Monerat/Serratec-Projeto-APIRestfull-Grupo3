package br.com.techtoy.techtoy.model.email;

import java.util.List;

public class Email {
    
    private String assunto;
    private String mensagem;
    private String remetente;
    private List<String> destinatarios;

    public Email(String assunto, String mensagem, List<String> destinatarios) {
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.remetente = "grupo3.serratec.apirestful23.2@gmail.com";
        this.destinatarios = destinatarios;
    }

    public Email() {
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public List<String> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<String> destinatarios) {
        this.destinatarios = destinatarios;
    }    
}
