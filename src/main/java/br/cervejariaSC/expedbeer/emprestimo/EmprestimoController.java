package br.cervejariaSC.expedbeer.emprestimo;

import br.cervejariaSC.expedbeer.exception.NotFoundException;
import br.cervejariaSC.expedbeer.usuario.Usuario;
import br.cervejariaSC.expedbeer.usuario.UsuarioService;
import br.cervejariaSC.expedbeer.util.Paginacao;
import br.cervejariaSC.expedbeer.vasilhame.Vasilhame;
import br.cervejariaSC.expedbeer.vasilhame.VasilhameService;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/emprestimo")
@AllArgsConstructor
@CrossOrigin("*")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioService usuarioService;
    private final VasilhameService vasilhameService;

    @PostMapping("/")
    public ResponseEntity<List<EmprestimoRepresentation.Detalhes>> criar(@Valid @RequestBody EmprestimoRepresentation.Create create) {

        Usuario usuario = usuarioService.getUsuarioById(create.getCliente());
        if (usuario.getTipo().equals(Usuario.Tipo.Cliente)) {
            List<Vasilhame> vasilhames = create.getVasilhames().stream().map(vasilhameService::buscarUmDisponivel).collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(EmprestimoRepresentation.Detalhes.from(emprestimoService.criar(usuario, vasilhames)));
        } else {
            throw new NotFoundException("Usuário para empréstimos precisa ser do tipo Cliente.");
        }
    }

    @PostMapping("/set_aguardando_coleta/{id}")
    public ResponseEntity<EmprestimoRepresentation.Detalhes> setAguardandoColeta(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EmprestimoRepresentation.Detalhes.from(emprestimoService.setAguardandoColeta(id)));
    }

    @PostMapping("/set_emprestado/{id}")
    public ResponseEntity<EmprestimoRepresentation.Detalhes> setEmprestado(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EmprestimoRepresentation.Detalhes.from(emprestimoService.setEmprestado(id)));
    }

    @PostMapping("/set_devolvido/{id}")
    public ResponseEntity<EmprestimoRepresentation.Detalhes> setDevolvido(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EmprestimoRepresentation.Detalhes.from(emprestimoService.setDevolvido(id)));
    }

    @GetMapping("/")
    public ResponseEntity<Paginacao> buscaFiltro(
            @QuerydslPredicate(root = Emprestimo.class) Predicate filtroURI,
            @RequestParam(name = "tamanhoPagina", defaultValue = "10") int tamanhoPagina,
            @RequestParam(name = "pagina", defaultValue = "0") int pagina,
            @RequestParam(name = "nomeCliente", defaultValue = "") String nomeCliente,
            @RequestParam(name = "idCliente", defaultValue = "0") Long idCliente,
            @RequestParam(name = "idVasilhame", defaultValue = "0") Long vasilhame) {

        BooleanExpression filtro = QEmprestimo.emprestimo.isNotNull();

        if (!Objects.isNull(filtroURI)) {
            filtro = filtro.and(filtroURI);
        }

        if (!nomeCliente.equals("")) {
            filtro = filtro.and(QEmprestimo.emprestimo.usuario.razao.containsIgnoreCase(nomeCliente));
        }

        if (!idCliente.equals(0L)) {
            filtro = filtro.and(QEmprestimo.emprestimo.usuario.id.eq(idCliente));
        }

        if (!vasilhame.equals(0L)) {
            filtro = filtro.and(QEmprestimo.emprestimo.vasilhame.id.eq(vasilhame));
        }

        Pageable paginaDesejada = PageRequest.of(pagina, tamanhoPagina);

        Page<Emprestimo> listaEmprestimo = this.emprestimoRepository.findAll(filtro, paginaDesejada);

        List<EmprestimoRepresentation.Detalhes> emprestimos = EmprestimoRepresentation.Detalhes.from(listaEmprestimo.getContent());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Paginacao.builder()
                        .paginaSelecionada(pagina)
                        .tamanhoPagina(tamanhoPagina)
                        .proximaPagina(listaEmprestimo.hasNext())
                        .conteudo(emprestimos)
                        .build());
    }

    @GetMapping("/historico/{cliente}")
    public ResponseEntity<Paginacao> buscaHistorico(
            @QuerydslPredicate(root = Emprestimo.class) Predicate filtroURI,
            @PathVariable("cliente") Long cliente,
            @RequestParam(name = "tamanhoPagina", defaultValue = "10") int tamanhoPagina,
            @RequestParam(name = "pagina", defaultValue = "0") int pagina,
            @RequestParam(name = "inicio", defaultValue = "") String inicio,
            @RequestParam(name = "fim", defaultValue = "") String fim) {

        BooleanExpression filtro = QEmprestimo.emprestimo.isNotNull();

        if (!Objects.isNull(filtroURI)) {
            filtro = filtro.and(filtroURI);
        }

        if (!cliente.equals(0L)) {
            filtro = filtro.and(QEmprestimo.emprestimo.usuario.id.eq(cliente));
        }

        if (!inicio.equals("")) {
            filtro = filtro.and(QEmprestimo.emprestimo.dataInicial.after(LocalDate.parse(inicio).minusDays(1)));
        }

        if (!fim.equals("")) {
            filtro = filtro.and(QEmprestimo.emprestimo.dataInicial.before(LocalDate.parse(fim).plusDays(1)));
        }

        Pageable paginaDesejada = PageRequest.of(pagina, tamanhoPagina);

        Page<Emprestimo> listaEmprestimo = this.emprestimoRepository.findAll(filtro, paginaDesejada);

        List<EmprestimoRepresentation.Detalhes> emprestimos = EmprestimoRepresentation.Detalhes.from(listaEmprestimo.getContent());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Paginacao.builder()
                        .paginaSelecionada(pagina)
                        .tamanhoPagina(tamanhoPagina)
                        .proximaPagina(listaEmprestimo.hasNext())
                        .conteudo(emprestimos)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoRepresentation.Detalhes> buscaUm(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EmprestimoRepresentation.Detalhes.from(emprestimoService.buscaUm(id)));
    }
}