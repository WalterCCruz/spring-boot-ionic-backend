package com.waltercruz.cursomc.repositories;

import com.waltercruz.cursomc.domain.Categoria;
import com.waltercruz.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {


    /*Somente fazendo um metodo com otipo de retorno e a nomenclatura pardao findByEmail o springdata já interpreta que
    preciso de um metodo de busca pelo campo especifico
     */
    @Transactional(readOnly = true)
    Cliente findByEmail(String email);


}




