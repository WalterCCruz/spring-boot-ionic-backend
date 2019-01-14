package com.waltercruz.cursomc.config;

import com.waltercruz.cursomc.domain.Security.JWTAuthenticationFilter;
import com.waltercruz.cursomc.domain.Security.JWTAuthorizationFilter;
import com.waltercruz.cursomc.domain.Security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)/*Anotacao necessaria para autorizar endpoints para perfis especificos no controller*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private Environment env;

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    JWTUtil JWTUtil;


    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**",
            "/produtos/**",
            "/categorias/**",

    };

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/h2-console/**",
            "/produtos/**",
            "/categorias/**",
            "/estados/**",
            "/clientes/**"
    };

    private static final String[] PUBLIC_MATCHERS_POST = {
            "/clientes/picture",
            "/auth/forgot/**"
    };


    protected void configure(HttpSecurity http) throws Exception {

        /*Configuracao p acessar h2*/
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        /*http.authorizeRequests().antMatchers(PUBLIC_MATCHERS)[ATÉ AQUI ESTOU DANDO PERMISSAO PARA TODOS DENTRO DESTE VETOR]
        .anyRequest().authenticated();[PARA TODOS OS DEMAIS SOLICITO AUTENTICACAO]
         */
        http.cors().and().csrf().disable();/*chamadno as configuracoes basicas do metodo abaixo e desabilitando configuracoes csrf pois nao guardo nada em sessao*/
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS).permitAll()
                .antMatchers(PUBLIC_MATCHERS_GET).permitAll()
                .antMatchers(PUBLIC_MATCHERS_POST).permitAll()
                .anyRequest().authenticated();

        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), JWTUtil));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), JWTUtil, userDetailsService));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }


    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*Quem é o userDetailsService e qual é o tipo de criptografia bCryptPasswordEncoder*/
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());    }


    @Bean
        /*PERMITIDNO ACESSO AOS MEUS ENDPOINT POR MULTIPLAS FONTES COM CONFIGURACOES BASICAS, P REQUISICOES AUTORIZADAS*/
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }






}
