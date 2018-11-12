package com.waltercruz.cursomc.repositories;

import com.waltercruz.cursomc.domain.Categoria;
import com.waltercruz.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {





}




