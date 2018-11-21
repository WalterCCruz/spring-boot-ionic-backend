package com.waltercruz.cursomc.services;


import com.waltercruz.cursomc.domain.Pedido;
import com.waltercruz.cursomc.repositories.PedidoRepository;
import com.waltercruz.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository categoriaRepository;


    public Pedido find (Integer id) {
        Optional<Pedido> obj = categoriaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }
}
