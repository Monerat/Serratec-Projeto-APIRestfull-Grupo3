package br.com.techtoy.techtoy.dto.usuario;

public class UsuarioLoginResponseDTO {

    private String token;

    private UsuarioResponseDTO usuario;

    public UsuarioLoginResponseDTO(String token, UsuarioResponseDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public UsuarioLoginResponseDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
    }
}
