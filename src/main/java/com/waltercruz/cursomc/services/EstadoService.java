package com.waltercruz.cursomc.services;


import com.waltercruz.cursomc.domain.Estado;
import com.waltercruz.cursomc.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;


    public List<Estado> findAll(){
        return estadoRepository.findAllByOrderByNome();
    }

}
