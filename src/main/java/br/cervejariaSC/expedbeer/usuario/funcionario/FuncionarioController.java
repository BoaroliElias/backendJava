package br.cervejariaSC.expedbeer.usuario.funcionario;

import br.cervejariaSC.expedbeer.usuario.*;
import br.cervejariaSC.expedbeer.util.Paginacao;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/funcionario")
@AllArgsConstructor
@CrossOrigin("*")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/")
    public ResponseEntity<FuncionarioRepresentation.Detail> criar(@Valid @RequestBody FuncionarioRepresentation.CreateUsuario create) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(FuncionarioRepresentation.Detail.from(this.funcionarioService.salvar(create)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioRepresentation.Detail> atualizar(@PathVariable("id") Long id, @Valid @RequestBody FuncionarioRepresentation.UpdateUsuario update) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(FuncionarioRepresentation.Detail.from(this.funcionarioService.atualizar(id, update)));
    }

    @PutMapping("/inativar/{id}")
    public ResponseEntity<FuncionarioRepresentation.Detail> inativaFuncionario(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(FuncionarioRepresentation.Detail.from(this.funcionarioService.inativar(id)));
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<FuncionarioRepresentation.Detail> ativaFuncionario(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(FuncionarioRepresentation.Detail.from(this.funcionarioService.ativar(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioRepresentation.Detail> buscaUmUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.ok(FuncionarioRepresentation.Detail.from(this.usuarioService.getUsuarioById(id)));
    }

    @GetMapping("/")
    public ResponseEntity<Paginacao> buscaComFiltro(
            @QuerydslPredicate(root = Usuario.class) Predicate filtroURI,
            @RequestParam(name = "tamanhoPagina", defaultValue = "10") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0")int pagina) {

        BooleanExpression filtro = QUsuario.usuario.tipo.eq(Usuario.Tipo.Funcionario);

        if (!Objects.isNull(filtroURI)) {
            filtro = filtro.and(filtroURI);
        }

        Pageable paginaDesejada = PageRequest.of(pagina, tamanhoPagina);

        Page<Usuario> listaFuncionario = this.usuarioRepository.findAll(filtro, paginaDesejada);

        List<FuncionarioRepresentation.Lista> funcionarios = FuncionarioRepresentation.Lista.from(listaFuncionario.getContent());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Paginacao.builder()
                        .paginaSelecionada(pagina)
                        .tamanhoPagina(tamanhoPagina)
                        .proximaPagina(listaFuncionario.hasNext())
                        .conteudo(funcionarios)
                        .build());
    }
}
