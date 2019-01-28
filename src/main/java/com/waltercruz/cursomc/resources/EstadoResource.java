package com.waltercruz.cursomc.resources;

import com.waltercruz.cursomc.DTO.CidadeDTO;
import com.waltercruz.cursomc.DTO.EstadoDTO;
import com.waltercruz.cursomc.domain.Cidade;
import com.waltercruz.cursomc.domain.Estado;
import com.waltercruz.cursomc.services.CidadeService;
import com.waltercruz.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAll() {
        List<Estado> list = estadoService.findAll();
        /*Esta utilizacao do DTO serve para que nao traga informacoes desnecessarias atreladas a categoria para este metodo ex: produtos
         * Com a utilizacao do lambda nao necessito realizar o for no DTO/Response para realizar a varredura e conversao das listas de Categoria
         * para CateGoriaDTO*/
        List<EstadoDTO> listDTO = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }


    @RequestMapping(value="/{estadoId}/cidades", method=RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
        List<Cidade> list = cidadeService.findByEstado(estadoId);
        List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

}
