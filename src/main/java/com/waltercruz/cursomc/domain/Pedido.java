package com.waltercruz.cursomc.domain;

import java.util.Date;

public class Pedido {

    private Integer id;
    private Date instant;

    private Pagamento pagamento;

    private Cliente cliente;

    private Endereco enderecoDeEntrega;

    public Pedido (){

    }

    public Pedido(Integer id, Date instant, Pagamento pagamento, Cliente cliente, Endereco enderecoDeEntrega) {
        this.id = id;
        this.instant = instant;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
    }
}
