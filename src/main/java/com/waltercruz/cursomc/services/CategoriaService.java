package com.waltercruz.cursomc.services;


import com.waltercruz.cursomc.services.exception.ObjectNotFoundException;
import com.waltercruz.cursomc.domain.Categoria;
import com.waltercruz.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;


    public Categoria find (Integer id) {
        Optional<Categoria> obj = categoriaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }


    public Categoria insert (Categoria obj){
        obj.setId(null);/*para garantir que o objeto que estou inserindo é novo*/
        return categoriaRepository.save(obj);
    }

    /*Categoria valendo null insere se já estiver preenchido id é realizada a edicao*/
    public Categoria update (Categoria obj){
        find(obj.getId());
        return categoriaRepository.save(obj);

    }

}
