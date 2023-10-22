package br.com.techtoy.techtoy.model.error;

public class ErrorResponse {
    private int status;
    private String title;
    private String message;
    private String dateHour;

    /**
     * Construtor para criar uma nova resposta de erro com atributos específicos.
     * 
     * @param status   Código de status HTTP associado ao erro.
     * @param title    Título descritivo do erro.
     * @param message  Mensagem de erro detalhada.
     * @param dateHour Data e hora em que o erro ocorreu.
     */
    public ErrorResponse(int status, String title, String message, String dateHour) {
        this.status = status;
        this.title = title;
        this.message = message;
        this.dateHour = dateHour;
    }

    // GETTERS E SETTERS

    /**
     * Obtém o código de status HTTP associado ao erro.
     * 
     * @return O código de status HTTP.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Define o código de status HTTP associado ao erro.
     * 
     * @param status O código de status HTTP a ser definido.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Obtém o título descritivo do erro.
     * 
     * @return O título descritivo do erro.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Define o título descritivo do erro.
     * 
     * @param title O título descritivo a ser definido.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Obtém a mensagem de erro detalhada.
     * 
     * @return A mensagem de erro detalhada.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Define a mensagem de erro detalhada.
     * 
     * @param message A mensagem de erro a ser definida.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Obtém a data e hora em que o erro ocorreu.
     * 
     * @return A data e hora do erro.
     */
    public String getDateHour() {
        return dateHour;
    }

    /**
     * Define a data e hora em que o erro ocorreu.
     * 
     * @param dateHour A data e hora a ser definida.
     */
    public void setDateHour(String dateHour) {
        this.dateHour = dateHour;
    }
}
