package com.waltercruz.cursomc.services;

import com.waltercruz.cursomc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void senderOrderConfirmationEmail(Pedido pedido);

    void sendEmail(SimpleMailMessage msg);



}
