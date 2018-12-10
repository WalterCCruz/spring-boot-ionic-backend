package com.waltercruz.cursomc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waltercruz.cursomc.domain.PagamentoComBoleto;
import com.waltercruz.cursomc.domain.PagamentoComCartao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {
    // https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass- without-hinting-the-pare
    //Codigo padrao exigencia da biblioteca do Jackson o que muda de projeto para projeto são as subclasses que tenho que registrar ex: PagamentoComCartao.class
    //Informacao que ficará disponivel que sera configurada no inicio da execucao da aplicacao.
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
            public void configure(ObjectMapper objectMapper) {
                objectMapper.registerSubtypes(PagamentoComCartao.class);
                objectMapper.registerSubtypes(PagamentoComBoleto.class);
                super.configure(objectMapper);
            }
        };
        return builder;
    }
}