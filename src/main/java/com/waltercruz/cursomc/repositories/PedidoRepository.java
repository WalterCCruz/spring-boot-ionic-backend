package com.waltercruz.cursomc.repositories;

import com.waltercruz.cursomc.domain.Categoria;
import com.waltercruz.cursomc.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {





}




