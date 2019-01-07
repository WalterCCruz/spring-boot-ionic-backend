package com.waltercruz.cursomc.DTO;

import com.waltercruz.cursomc.domain.Cidade;
import com.waltercruz.cursomc.domain.Estado;

import java.io.Serializable;
import java.util.List;

public class EstadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;
    private String nome;


    public EstadoDTO(){}

   public EstadoDTO( Estado obj){
        id = obj.getId();
        nome = obj.getNome();
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


}
