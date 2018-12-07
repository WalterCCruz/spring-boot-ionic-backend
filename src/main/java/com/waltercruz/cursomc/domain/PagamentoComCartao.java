package com.waltercruz.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.waltercruz.cursomc.domain.Enums.EstadoPagamento;

import javax.persistence.Entity;

@Entity
/*passando o nome da classe para o meu Json*/
@JsonTypeName("pagamentocomcartao")
public class PagamentoComCartao extends Pagamento{

    private static final long serialVersionUID = 1L;

    private Integer numeroDeparcelas;


    public PagamentoComCartao(){

    }


    public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeparcelas) {
        super(id, estado, pedido);
        this.numeroDeparcelas = numeroDeparcelas;
    }


    public Integer getNumeroDeparcelas() {
        return numeroDeparcelas;
    }

    public void setNumeroDeparcelas(Integer numeroDeparcelas) {
        this.numeroDeparcelas = numeroDeparcelas;
    }
}
