package com.waltercruz.cursomc.services;

import com.waltercruz.cursomc.domain.Cliente;
import com.waltercruz.cursomc.repositories.ClienteRepository;
import com.waltercruz.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private EmailService emailService;

    private Random rand = new Random();


    public void sendNewPassword(String email) {

        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassword();
        cliente.setSenha(bCryptPasswordEncoder.encode(newPass));
        clienteRepository.save(cliente);
        emailService.sendNewPasswordEmail(cliente, newPass);

    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if (opt == 0) {// gera um digito

            //Aqui estou gerando um numero aleatorio de 0 a 9 (rand.nextInt(10) e o 48 é o numero do 0 na tabela unicode
            return (char)(rand.nextInt(10) + 48);

        } else if (opt == 1) {// gera letra maiuscula
            //Aqui estou gerando uma letra maiuscula que tem 26 possibilidades e a primeira letra maiuscula na tabela unicode tem o valor 65
            return (char)(rand.nextInt(26) + 65);

        }else{//gera letra minuscula
            //Aqui estou gerando uma letra maiuscula que tem 26 possibilidades e a primeira letra maiuscula na tabela unicode tem o valor 97
            return (char)(rand.nextInt(26) + 97);
        }

    }

}
