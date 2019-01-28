package com.waltercruz.cursomc.config;


import com.waltercruz.cursomc.services.DBService;
import com.waltercruz.cursomc.services.EmailService;
import com.waltercruz.cursomc.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("prod")
public class ProdConfig {

    @Autowired
    private DBService dbService;

    @Value("$spring.jpa.hibernate.ddl-auto")
    private String strategy;

    @Bean
    public Boolean instantiateDatabase() throws ParseException {

        if (!"create".equals(strategy)) {
            dbService.instantiateDatabase();
            return false;
        }
          dbService.instantiateDatabase();
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }

}
