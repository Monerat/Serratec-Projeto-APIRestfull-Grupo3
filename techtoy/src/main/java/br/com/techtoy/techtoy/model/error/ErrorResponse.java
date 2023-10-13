package br.com.techtoy.techtoy.model.error;

public class ErrorResponse {
    private int status;
    private String title;
    private String message;
    private String dateHour;
    
    public ErrorResponse(int status, String title, String message, String dateHour) {
        this.status = status;
        this.title = title;
        this.message = message;
        this.dateHour = dateHour;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getDateHour() {
        return dateHour;
    }
    public void setDateHour(String dateHour) {
        this.dateHour = dateHour;
    }
    
}
