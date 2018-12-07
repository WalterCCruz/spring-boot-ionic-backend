package com.waltercruz.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class ItemPedido {

    private static final long serialVersionUID = 1L;

    /*Atributo composto*/
    /*@EmbeddedId para u m id embutido em um metodo auxiliar*/
    /*@JsonIgnore nao serializar*/
    @JsonIgnore
    @EmbeddedId
    private ItemPedidoPK id = new ItemPedidoPK();

    private Double desconto;
    private Integer quantidade;
    private Double preco;



    /*só de usar o get na frente do nome do metodo o Json já exibe o resultado do return*/
    public double getSubTotal(){
        return (preco - desconto) * quantidade;
    }


    public ItemPedido (){

    }

    public ItemPedido(Pedido peiddo, Produto produto, Double desconto, Integer quantidade, Double preco) {
        super();
        id.setPedido(peiddo);
        id.setProduto(produto);
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
    }


    @JsonIgnore
    public Pedido getPedido(){
        return id.getPedido();
    }


    public Produto getProduto(){
        return id.getProduto();
    }

    public ItemPedidoPK getId(){
        return id;
    }


    public void setId(ItemPedidoPK id) {
        this.id = id;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ItemPedido other = (ItemPedido) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}