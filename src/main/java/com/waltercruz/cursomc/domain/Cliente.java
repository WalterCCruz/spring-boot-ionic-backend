package com.waltercruz.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.waltercruz.cursomc.domain.Enums.Perfil;
import com.waltercruz.cursomc.domain.Enums.TipoCliente;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Cliente {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String nome;

    @Column(unique = true)
    private String email;

    private String cpfOuCnpj;
    private Integer tipo;

    /*@JsonManagedReference serve para tratar a referencia ciclica - ir na cliente pegar os enderecos, ir nos enderecos e pegar os clientes em loop*/
    //@JsonManagedReference   Alterado do outro lado por JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco>enderecos = new ArrayList<>();


    /*Set é um tipo de lista que não permite repeticao*/
    /* @ElementCollection para ser mapeado como uma entidade fraca*/
    /* @CollectionTable para criar a tabela auxiliar para guardar o telefone*/
    @ElementCollection
    @CollectionTable(name = "telefone")
    private Set<String>telefones = new HashSet<>();

    /*Os pedidos de um cliente nao serao serializados - tratamento referencia ciclica*/
    //@JsonBackReference  Alterado para JsonIgnore
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos =  new ArrayList<>();

    @JsonIgnore
    private String senha;

    /*fetch=FetchType.EAGER estou garantindo que os perfis virao com os clientes quando consultado*/
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    private Set<Integer> perfis = new HashSet<>();


    public Cliente(){
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
        this.id = id;
        this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = (tipo == null) ? null : tipo.getCod();/*ternario*/
        addPerfil(Perfil.CLIENTE);
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public TipoCliente getTipo() {
        return TipoCliente.toEnum(tipo);
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo.getCod();
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Set<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<String> telefones) {
        this.telefones = telefones;
    }


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Perfil>getPerfis(){
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil){
        perfis.add(perfil.getCod());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
