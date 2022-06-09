package br.cervejariaSC.expedbeer.vasilhame;

import br.cervejariaSC.expedbeer.emprestimo.Emprestimo;
import br.cervejariaSC.expedbeer.emprestimo.EmprestimoService;
import br.cervejariaSC.expedbeer.tipoVasilhame.TipoVasilhame;
import br.cervejariaSC.expedbeer.tipoVasilhame.TipoVasilhameService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vasilhame")
@AllArgsConstructor
@CrossOrigin("*")
public class VasilhameController {

    private final VasilhameService vasilhameService;
    private final VasilhameRepository vasilhameRepository;
    private final TipoVasilhameService tipoVasilhameService;
    private final EmprestimoService emprestimoService;

    @PostMapping("/")
    public ResponseEntity<VasilhameRepresentation.Detalhes> cadastrarVasilhame(
        @Valid @RequestBody VasilhameRepresentation.CreateOrUpdateVasilhame create) {

        TipoVasilhame tipoVasilhame = tipoVasilhameService.buscarUm(create.getTipoVasilhame());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(VasilhameRepresentation
                        .Detalhes.from(this.vasilhameService.criar(tipoVasilhame)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VasilhameRepresentation.Detalhes> alterarVasilhame(
            @PathVariable("id") Long id,
            @Valid @RequestBody VasilhameRepresentation.CreateOrUpdateVasilhame update) {

        TipoVasilhame tipoVasilhame = tipoVasilhameService.buscarUm(update.getTipoVasilhame());

        return ResponseEntity.status(HttpStatus.OK)
                .body(VasilhameRepresentation
                        .Detalhes.from(this.vasilhameService.alterar(id, tipoVasilhame)));
    }

    @GetMapping("/")
    public ResponseEntity<Paginacao> buscaFiltro(
            @QuerydslPredicate(root = Vasilhame.class) Predicate filtroURI,
            @RequestParam(name = "tamanhoPagina", defaultValue = "10") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0")int pagina,
            @RequestParam(name = "nomeCliente", defaultValue = "") String nomeCliente) {

        BooleanExpression filtro = QVasilhame.vasilhame.isNotNull();

        if (!Objects.isNull(filtroURI)) {
            filtro = filtro.and(filtroURI);
        }

        if (!nomeCliente.equals("")) {
            List<Emprestimo> emprestimos = emprestimoService.buscaEmprestimoNoCliente(nomeCliente);
            filtro = filtro.and(QVasilhame.vasilhame.id.in(emprestimos.stream().map(x -> x.getVasilhame().getId()).distinct().collect(Collectors.toList())));
        }

        Pageable paginaDesejada = PageRequest.of(pagina, tamanhoPagina);

        Page<Vasilhame> listaVasilhame = this.vasilhameRepository.findAll(filtro, paginaDesejada);

        List<VasilhameRepresentation.Detalhes> vasilhames = VasilhameRepresentation.Detalhes.from(listaVasilhame.getContent());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Paginacao.builder()
                        .paginaSelecionada(pagina)
                        .tamanhoPagina(tamanhoPagina)
                        .proximaPagina(listaVasilhame.hasNext())
                        .conteudo(vasilhames)
                        .build());

    }

    @GetMapping("/{id}")
    public ResponseEntity<VasilhameRepresentation.Detalhes> buscarUm(
            @PathVariable("id") Long id){

        return ResponseEntity.ok(VasilhameRepresentation.Detalhes
                .from(this.vasilhameService.buscarUm(id)));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<VasilhameRepresentation.Detalhes>> buscarTodos() {

        List<Vasilhame> todos = this.vasilhameRepository.findAll();

        return ResponseEntity.ok(VasilhameRepresentation.Detalhes.from(todos));
    }

    @PutMapping("/descarta/{id}")
    public ResponseEntity<VasilhameRepresentation.Detalhes> descarta(@PathVariable("id") Long id){
        return ResponseEntity.ok(VasilhameRepresentation.Detalhes
                .from(this.vasilhameService.setDescartado(this.vasilhameService.buscarUm(id))));
    }

    @PutMapping("/estorna_descarte/{id}")
    public ResponseEntity<VasilhameRepresentation.Detalhes> estornaDescarte(@PathVariable("id") Long id){
        return ResponseEntity.ok(VasilhameRepresentation.Detalhes
                .from(this.vasilhameService.estornaDescarte(this.vasilhameService.buscarUm(id))));
    }

}
