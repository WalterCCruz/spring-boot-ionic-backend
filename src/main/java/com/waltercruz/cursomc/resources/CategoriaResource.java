package com.waltercruz.cursomc.resources;

import com.waltercruz.cursomc.services.CategoriaService;
import com.waltercruz.cursomc.domain.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


/* O RestController serializa o objeto para que a resposta seja exibida, tornando disponsável o uso do @ResponseBody no método que irá retornar*/
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;

    /*PathVariable é a variável que virá da URL conforme o parametro solciitado acima no RequestMapping */
    /*Response Entity já encapusla o meu objeto, dispensando a necessidade de constuir uma classe de ResponseCategoria*/
    @RequestMapping(value= "/{id}",method = RequestMethod.GET)
    public ResponseEntity<Categoria> find(@PathVariable Integer id) {
        Categoria obj = categoriaService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    /*@RequestBody esta annotation permite que vc transforme seu json automaticamente para seu objeto de domain (entidade)*/
    public ResponseEntity<Void> insert(@RequestBody Categoria obj){
        obj = categoriaService.insert(obj);
        /*Neste trechoestou associando para URL o id criado para o novo objeto e já atribuindo o seu direcionamento*/
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value= "/{id}", method = RequestMethod.PUT)
    public ResponseEntity <Void>update(@RequestBody Categoria obj, @PathVariable Integer id){
        obj.setId(id);
        obj = categoriaService.update(obj);
        return ResponseEntity.noContent().build();
    }


}
