package br.cervejariaSC.expedbeer.usuario;

import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>, QuerydslPredicateExecutor<Usuario> {

    List<Usuario> findAll(Predicate filter);

}