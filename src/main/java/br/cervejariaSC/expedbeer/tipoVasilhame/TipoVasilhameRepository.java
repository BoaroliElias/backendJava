package br.cervejariaSC.expedbeer.tipoVasilhame;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TipoVasilhameRepository extends PagingAndSortingRepository<TipoVasilhame, Long>,
        QuerydslPredicateExecutor<TipoVasilhame> {

    List<TipoVasilhame> findAll();
}
