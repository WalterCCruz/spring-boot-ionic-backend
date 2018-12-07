package com.waltercruz.cursomc.resources;

import com.waltercruz.cursomc.DTO.CategoriaDTO;
import com.waltercruz.cursomc.DTO.ProdutoDTO;
import com.waltercruz.cursomc.domain.Categoria;
import com.waltercruz.cursomc.domain.Produto;
import com.waltercruz.cursomc.resources.utils.URL;
import com.waltercruz.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/* O RestController serializa o objeto para que a resposta seja exibida, tornando disponsável o uso do @ResponseBody no método que irá retornar*/
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    /*PathVariable é a variável que virá da URL conforme o parametro solciitado acima no RequestMapping */
    /*Response Entity já encapusla o meu objeto, dispensando a necessidade de constuir uma classe de ResponseCategoria*/
    @RequestMapping(value= "/{id}",method = RequestMethod.GET)
    public ResponseEntity<Produto> find(@PathVariable Integer id) {
        Produto obj = produtoService.find(id);
        return ResponseEntity.ok().body(obj);
    }


    @RequestMapping(method = RequestMethod.GET)
    /*@RequestParam faz com que o parametro se torne opcional*/
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value="nome", defaultValue="") String nome,
            @RequestParam(value="categorias", defaultValue="") String categorias,
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction) {
        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> list = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }






}
