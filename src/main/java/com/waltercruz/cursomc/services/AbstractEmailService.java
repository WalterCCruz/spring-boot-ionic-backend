package com.waltercruz.cursomc.services;

import com.waltercruz.cursomc.domain.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("{default.sender}")
    private String sender;

    @Override
    public void senderOrderConfirmationEmail(Pedido obj){

        SimpleMailMessage sm = prepareSimpleMailMesssage(obj);
        sendEmail(sm);

    }

    protected SimpleMailMessage prepareSimpleMailMesssage(Pedido obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getCliente().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido confirmado. Codigo: " + obj.getId());
        sm.setSentDate((new Date(System.currentTimeMillis())));
        sm.setText(obj.toString());
        return sm;

    }

}
