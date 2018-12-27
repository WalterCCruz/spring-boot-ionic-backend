package com.waltercruz.cursomc;

import com.waltercruz.cursomc.domain.*;
import com.waltercruz.cursomc.domain.Enums.EstadoPagamento;
import com.waltercruz.cursomc.domain.Enums.TipoCliente;
import com.waltercruz.cursomc.repositories.*;
import com.waltercruz.cursomc.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/* CommandLineRunner Executar a instanciação no momento que a app iniciar*/
@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {



    @Autowired
    private S3Service s3Service;

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        s3Service.uploadFile("C:\\java.jpg");
    }
}
