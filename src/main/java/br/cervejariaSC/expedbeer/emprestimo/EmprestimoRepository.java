package br.cervejariaSC.expedbeer.emprestimo;

import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EmprestimoRepository extends PagingAndSortingRepository<Emprestimo, Long>, QuerydslPredicateExecutor<Emprestimo> {

    List<Emprestimo> findAll(Predicate filter);
    List<Emprestimo> findAll();
}