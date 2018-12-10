package com.waltercruz.cursomc;

import com.waltercruz.cursomc.domain.*;
import com.waltercruz.cursomc.domain.Enums.EstadoPagamento;
import com.waltercruz.cursomc.domain.Enums.TipoCliente;
import com.waltercruz.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;


/* CommandLineRunner Executar a instanciação no momento que a app iniciar*/
@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
