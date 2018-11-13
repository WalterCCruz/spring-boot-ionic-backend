package com.waltercruz.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;


@Entity
public class Endereco {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;


    /*@JsonBackReference Aqui estou dizendo que o endereço não pode serializaro cliente*/
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name="cidade_id")
    private Cidade cidade;


    public Endereco(){

    }



    public Endereco(Integer id, String logradouro, String numero, String complemento, String cep, Cliente cliente, Cidade cidade ) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.cliente = cliente;
        this.cidade = cidade;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(id, endereco.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Entity
    public static class ItemPedido {

        private static final long serialVersionUID = 1L;

        /*Atributo composto*/
        /*@EmbeddedId para u m id embutido em um metodo auxiliar*/
        @EmbeddedId
        private ItemPedidoPK id = new ItemPedidoPK();

        private Double desconto;
        private Integer quantidade;
        private Double preco;


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
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemPedido that = (ItemPedido) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
