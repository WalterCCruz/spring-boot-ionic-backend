package com.waltercruz.cursomc.domain;

import java.io.Serializable;
import java.util.Objects;

/*Serializable permite que seus objetos sejam convertidos para uma sequencia de bytes e assim possam ser gravados, trafegar na rede e ect.*/
public class Categoria implements Serializable {

   private static final long serialVersionUID = 1L;


    private Integer id;
    private String nome;


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

    public Categoria (){

    }

    public Categoria(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id) &&
                Objects.equals(nome, categoria.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
