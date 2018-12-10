package com.waltercruz.cursomc.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.waltercruz.cursomc.domain.Enums.EstadoPagamento;

import javax.persistence.*;
import java.util.Objects;


/*Inheritance mapeamento e herança da superclasse*/
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
/*Adicionando um campo adicional ao Json @Type*/
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public  abstract class Pagamento {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private Integer estado;

    /*@MapsId para que o Id do pagamento seja o mesmo do id do pedido*/
    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
   // @JsonBackReference/*tratamento  -  nao permito que o pedido seja serializado*/ Alterado p JsonIgnore
    @JsonIgnore
    private Pedido pedido;


    public Pagamento (){

    }


    public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
        this.id = id;
        this.estado = (estado == null) ? null : estado.getCod();/*ternario*/
        this.pedido = pedido;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoPagamento getEstado() {
        return EstadoPagamento.toEnum(estado);
    }

    public void setEstado(EstadoPagamento estado) {
        this.estado = estado.getCod();
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(id, pagamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
