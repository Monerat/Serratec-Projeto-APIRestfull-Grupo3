package br.com.techtoy.techtoy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Aqui informo que é uma classe se configuração de segurança do springSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Metodo que devolve uma instnacia do objeto que sabe codificar e decodigicar
     * pelo springSecurity.
     * Isso não tem nada a ver com JWT
     * Aqui será usaro para codificar a senha do usuario
     * 
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Metodo padrão para condifurar o nosso customUserDetailService com o nosso
     * metodo de codificar.
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Metodo que tem a configuracção global de acessos e permissoes por rotas.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Parte padrão das condifuracoes, por enquanto ignorar.
        http
                .cors().and().csrf().disable()

                .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                /**
                 * Daqui pra baixo é onde vamos futucar e fazer nossas validações dinamicas.
                 * Aqui vamos informar as rotas que vão ou não precisar de autenticação e ou
                 * autorização.
                 */
                .antMatchers(HttpMethod.POST, "/api/usuarios", "/api/usuarios/login")
                .permitAll()// informo que todos podem acessar esses endpoints sem autenticação.
                .antMatchers(HttpMethod.GET, "/api/produtos/public", "/api/produtos/public/{id}",
                        "/api/categorias/public/", "/api/categorias/public/{id}", "/api/categorias/public/nome/{nome}",
                        "api/usuarios/public/")
                .permitAll()
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**",
                        "/swagger-ui.html")
                .permitAll()
                .anyRequest()
                .authenticated(); // Digo que qualquer outro endpoint não maapeado acima deve cobrar autenticação.

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
