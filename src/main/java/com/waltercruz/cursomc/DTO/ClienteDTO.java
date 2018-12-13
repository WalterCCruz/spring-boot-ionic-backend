package com.waltercruz.cursomc.DTO;

import com.waltercruz.cursomc.domain.Cliente;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ClienteDTO  implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Length(min = 5,  max = 80, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Email(message = "email invalido")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatorio")
    private String senha;



    public ClienteDTO(Cliente obj){
        id = obj.getId();
        nome = obj.getNome();
        email = obj.getEmail();
        senha = obj.getSenha();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ClienteDTO(){

    }



}
