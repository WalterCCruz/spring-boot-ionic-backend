package com.waltercruz.cursomc.resources;

import com.waltercruz.cursomc.DTO.CategoriaDTO;
import com.waltercruz.cursomc.services.CategoriaService;
import com.waltercruz.cursomc.domain.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


/* O RestController serializa o objeto para que a resposta seja exibida, tornando disponsável o uso do @ResponseBody no método que irá retornar*/
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;

    /*PathVariable é a variável que virá da URL conforme o parametro solciitado acima no RequestMapping */
    /*Response Entity já encapusla o meu objeto, dispensando a necessidade de constuir uma classe de ResponseCategoria*/
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Categoria> find(@PathVariable Integer id) {
        Categoria obj = categoriaService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    /*@RequestBody esta annotation permite que vc transforme seu json automaticamente para seu objeto de domain (entidade)*/
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objdto) {
        Categoria obj = categoriaService.fromDTO(objdto);
        obj = categoriaService.insert(obj);
        /*Neste trecho estou associando para URL o id criado para o novo objeto e já atribuindo o seu direcionamento*/
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {
        obj.setId(id);
        obj = categoriaService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<Categoria> list = categoriaService.findAll();
        /*Esta utilizsacao do DTO serve para que nao traga informacoes desnecessarias atraladas a categoria para este metodo ex: produtos
         * Com a utilizacao do lambda nao necessito realizar o for no DTO/Response para realizar a varredura e conversao das listas de Categoria
         * para CateGoriaDTO*/
        List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    /*@RequestParam faz com que o parametro se torne opcional*/
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Categoria> list = categoriaService.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }


}
