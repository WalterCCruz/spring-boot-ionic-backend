package com.waltercruz.cursomc.repositories;

import com.waltercruz.cursomc.domain.Categoria;
import com.waltercruz.cursomc.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Transactional
    /*@Query torna desnecessario criar uma nova classe para implementacao deste metodo permitindo inserir direto na annotation*/
    @Query("Select DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
    Page<Produto> search(@Param("nome") String nome,@Param("categorias") List<Categoria> categorias, Pageable pageRequest);


    /* Este método faz a mesma coisa que o de cima, porém utilizando o Spring Data seguindo os padroes de nomenclatura*/
    Page<Produto>findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);


}




