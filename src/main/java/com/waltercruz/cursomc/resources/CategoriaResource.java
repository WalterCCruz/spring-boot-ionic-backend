package com.waltercruz.cursomc.resources;

import com.waltercruz.cursomc.domain.Categoria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/* O RestController serializa o objeto para que a resposta seja exibida, tornando disponsável o uso do @ResponseBody no método que irá retornar*/
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @RequestMapping(method = RequestMethod.GET)
    public List<Categoria> listar() {


        Categoria cat1 = new Categoria(1, "Informatica");
        Categoria cat2 = new Categoria(2, "Escritorio");

        List<Categoria> lista = new ArrayList<Categoria>();
        lista.add(cat1);
        lista.add(cat2);

        return lista;
    }


}
