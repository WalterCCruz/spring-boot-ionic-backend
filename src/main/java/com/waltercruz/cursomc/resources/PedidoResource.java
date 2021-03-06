package com.waltercruz.cursomc.resources;

import com.waltercruz.cursomc.DTO.CategoriaDTO;
import com.waltercruz.cursomc.domain.Categoria;
import com.waltercruz.cursomc.domain.Pedido;
import com.waltercruz.cursomc.services.CategoriaService;
import com.waltercruz.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


/* O RestController serializa o objeto para que a resposta seja exibida, tornando disponsável o uso do @ResponseBody no método que irá retornar*/
@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService categoriaService;

    /*PathVariable é a variável que virá da URL conforme o parametro solciitado acima no RequestMapping */
    /*Response Entity já encapusla o meu objeto, dispensando a necessidade de constuir uma classe de ResponseCategoria*/
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pedido> find(@PathVariable Integer id) {
        Pedido obj = categoriaService.find(id);
        return ResponseEntity.ok().body(obj);
    }


    @RequestMapping(method = RequestMethod.POST)
    /*@RequestBody esta annotation permite que vc transforme seu json automaticamente para seu objeto de domain (entidade)*/
    /*Nao foi utilizado Dto pois existem varios relacionamentos e iria ficar muito extenso*/
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
         obj = categoriaService.insert(obj);
        /*Neste trecho estou associando para URL o id criado para o novo objeto e já atribuindo o seu direcionamento*/
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    /*@RequestParam faz com que o parametro se torne opcional*/
    public ResponseEntity<Page<Pedido>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "instante") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        Page<Pedido> list = categoriaService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }



}
