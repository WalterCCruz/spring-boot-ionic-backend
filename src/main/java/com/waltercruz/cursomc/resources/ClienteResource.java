package com.waltercruz.cursomc.resources;

import com.waltercruz.cursomc.DTO.ClienteDTO;
import com.waltercruz.cursomc.DTO.ClienteNewDTO;
import com.waltercruz.cursomc.domain.Cliente;
import com.waltercruz.cursomc.domain.Cliente;
import com.waltercruz.cursomc.services.ClienteService;
import com.waltercruz.cursomc.services.ClienteService;
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
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    /*PathVariable é a variável que virá da URL conforme o parametro solciitado acima no RequestMapping */
    /*Response Entity já encapusla o meu objeto, dispensando a necessidade de constuir uma classe de ResponseCliente*/
    @RequestMapping(value= "/{id}",method = RequestMethod.GET)
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {
        Cliente obj = clienteService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Cliente obj, @PathVariable Integer id) {
        obj.setId(id);
        obj = clienteService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> list = clienteService.findAll();
        /*Esta utilizsacao do DTO serve para que nao traga informacoes desnecessarias atraladas a categoria para este metodo ex: produtos
         * Com a utilizacao do lambda nao necessito realizar o for no DTO/Response para realizar a varredura e conversao das listas de Cliente
         * para CateGoriaDTO*/
        List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    /*@RequestParam faz com que o parametro se torne opcional*/
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Cliente> list = clienteService.findPage(page, linesPerPage, orderBy, direction);
        Page<ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }

    @RequestMapping(method = RequestMethod.POST)
    /*@RequestBody esta annotation permite que vc transforme seu json automaticamente para seu objeto de domain (entidade)*/
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objdto) {
        Cliente obj = clienteService.fromDTO(objdto);
        obj = clienteService.insert(obj);
        /*Neste trecho estou associando para URL o id criado para o novo objeto e já atribuindo o seu direcionamento*/
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }




}
