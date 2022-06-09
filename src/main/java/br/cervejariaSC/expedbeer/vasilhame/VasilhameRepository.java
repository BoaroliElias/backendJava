package br.cervejariaSC.expedbeer.vasilhame;

import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface VasilhameRepository extends PagingAndSortingRepository<Vasilhame, Long>,
        QuerydslPredicateExecutor<Vasilhame> {

    List<Vasilhame> findAll(Predicate filter);
    List<Vasilhame> findAll();
}
