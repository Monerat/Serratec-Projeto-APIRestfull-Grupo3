package br.com.techtoy.techtoy.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.techtoy.techtoy.model.Enum.EnumTipoUsuario;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    // #region Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Date dataCadastro;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private EnumTipoUsuario perfil;

    @OneToMany(mappedBy = "usuario")
    private transient List<Log> logs;

    @OneToMany(mappedBy = "usuario")
    private transient List<Pedido> pedidos;

    /**
     * Construtor que inicializa um objeto de usuário com os parâmetros fornecidos.
     *
     * @param id           O ID do usuário.
     * @param nome         O nome do usuário.
     * @param dataCadastro A data de cadastro do usuário.
     * @param telefone     O número de telefone do usuário.
     * @param email        O endereço de e-mail do usuário.
     * @param senha        A senha do usuário.
     * @param perfil       O perfil de usuário.
     * @param log          O registro de Log associado ao usuário.
     * @param pedido       O registro de Pedido associado ao usuário.
     */

    public Usuario(Long id, String nome, String dataCadastro, String telefone, String email, String senha,
            EnumTipoUsuario perfil,
            Log log, Pedido pedido) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.dataCadastro = new Date();
        this.logs = new ArrayList<>();
        this.logs.add(log);
        this.pedidos = new ArrayList<>();
        this.pedidos.add(pedido);
    }

    /**
     * Construtor que inicializa um objeto de usuário com um ID específico.
     *
     */

    public Usuario(Long id) {
        this.dataCadastro = new Date();
        this.id = id;
    }

    /**
     * Construtor que inicializa um objeto de usuário com valores padrão.
     */

    public Usuario() {
        this.dataCadastro = new Date();
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public EnumTipoUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(EnumTipoUsuario perfil) {
        this.perfil = perfil;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> perfis = new ArrayList<>();
        perfis.add(perfil.toString());

        // Converter a lista de perfis em uma lista de Authorities
        return perfis.stream()
                .map(perf -> new SimpleGrantedAuthority(perf))
                // .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return getSenha();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}