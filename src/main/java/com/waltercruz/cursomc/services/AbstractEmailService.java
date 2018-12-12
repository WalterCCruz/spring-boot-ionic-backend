package com.waltercruz.cursomc.services;

import com.waltercruz.cursomc.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {


    @Autowired
    private TemplateEngine templateEngine;



    @Value("{default.sender}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;


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


    protected String htmlFromTemplatePedido(Pedido obj){
        Context context= new Context();
        context.setVariable("pedido",obj);
        return templateEngine.process("email/confirmacaoPedido",context);
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Pedido obj){
        try {
            MimeMessage mm = PrepareMimeMessageFromPedido(obj);{
                sendHtmlEmail(mm);
            }
        }catch (MessagingException e){
            senderOrderConfirmationEmail(obj);
        }
    }

     protected MimeMessage PrepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
         MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage , true);
         mmh.setTo(obj.getCliente().getEmail());
         mmh.setSubject("Pedido confirmado código: " + obj.getId());
         mmh.setSentDate(new Date(System.currentTimeMillis()));
         mmh.setText(htmlFromTemplatePedido(obj),true);
         return mimeMessage;

    }


}
