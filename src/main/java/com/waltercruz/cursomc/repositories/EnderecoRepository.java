package com.waltercruz.cursomc.repositories;

import com.waltercruz.cursomc.domain.Cliente;
import com.waltercruz.cursomc.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {





}




