package com.waltercruz.cursomc.services;


import com.waltercruz.cursomc.DTO.ClienteDTO;
import com.waltercruz.cursomc.domain.Cliente;
import com.waltercruz.cursomc.domain.Cliente;
import com.waltercruz.cursomc.repositories.ClienteRepository;
import com.waltercruz.cursomc.services.exception.DataIntegrityException;
import com.waltercruz.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    public Cliente find (Integer id) {
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }


    public Cliente insert (Cliente obj){
        obj.setId(null);/*para garantir que o objeto que estou inserindo é novo*/
        return clienteRepository.save(obj);
    }

    /*Cliente valendo null insere se já estiver preenchido id é realizada a edicao*/
    public Cliente update (Cliente obj){
        Cliente newObj = find(obj.getId());
        updateData(newObj , obj);
        return clienteRepository.save(newObj);

    }


    public void delete (Integer id){
        find(id);
        try {
            clienteRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possivel deletar pois há entidades relacionadas.");
        }

    }


    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPePage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page,linesPePage, Sort.Direction.valueOf(direction),orderBy);
        return clienteRepository.findAll(pageRequest);
    }


    public Cliente fromDTO (ClienteDTO objetoDto){
        return new Cliente(objetoDto.getId(),objetoDto.getNome(),objetoDto.getEmail(),null,null) ;
    }


    private void updateData (Cliente newObj, Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }



}
