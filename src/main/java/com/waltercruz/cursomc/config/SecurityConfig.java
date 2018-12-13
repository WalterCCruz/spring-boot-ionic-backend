package com.waltercruz.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private Environment env;


    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**",
            "/produtos/**",
            "/categorias/**"
    };

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/h2-console/**",
            "/produtos/**",
            "/categorias/**"
    };


    protected void configure(HttpSecurity http) throws Exception {

        /*Configuracao p acessar h2*/
        if(Arrays.asList(env.getActiveProfiles()).contains("test")){
            http.headers().frameOptions().disable();
        }

        /*http.authorizeRequests().antMatchers(PUBLIC_MATCHERS)[ATÉ AQUI ESTOU DANDO PERMISSAO PARA TODOS DENTRO DESTE VETOR]
        .anyRequest().authenticated();[PARA TODOS OS DEMAIS SOLICITO AUTENTICACAO]
         */
        http.cors().and().csrf().disable();/*chamadno as configuracoes basicas do metodo abaixo e desabilitando configuracoes csrf pois nao guardo nada em sessao*/
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS).permitAll()
                .antMatchers(PUBLIC_MATCHERS_GET).permitAll()
                .anyRequest().authenticated();
    }


    @Bean
        /*PERMITIDNO ACESSO AOS MEUS ENDPOINT POR MULTIPLAS FONTES COM CONFIGURACOES BASICAS*/
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }


}
