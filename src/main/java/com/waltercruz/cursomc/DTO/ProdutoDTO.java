package com.waltercruz.cursomc.DTO;

public class ProdutoDTO {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String nome;

    private Double valor;

    public ProdutoDTO(){

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
