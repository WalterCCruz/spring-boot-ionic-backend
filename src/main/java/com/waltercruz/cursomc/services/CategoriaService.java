package com.waltercruz.cursomc.services;


import com.waltercruz.cursomc.DTO.CategoriaDTO;
import com.waltercruz.cursomc.services.exception.DataIntegrityException;
import com.waltercruz.cursomc.services.exception.ObjectNotFoundException;
import com.waltercruz.cursomc.domain.Categoria;
import com.waltercruz.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Categoria newObj = find(obj.getId());
        updateData(newObj , obj);
        return categoriaRepository.save(newObj);


    }


    public void delete (Integer id){
        find(id);
        try {
            categoriaRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
        }

    }


    public List<Categoria>findAll(){
        return categoriaRepository.findAll();
    }

    public Page<Categoria>findPage(Integer page, Integer linesPePage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page,linesPePage, Sort.Direction.valueOf(direction),orderBy);
        return categoriaRepository.findAll(pageRequest);
    }


    public Categoria fromDTO (CategoriaDTO objetoDto){
        return new Categoria(objetoDto.getId(),objetoDto.getNome());
    }


    private void updateData (Categoria newObj, Categoria obj){
        newObj.setNome(obj.getNome());

    }


}
