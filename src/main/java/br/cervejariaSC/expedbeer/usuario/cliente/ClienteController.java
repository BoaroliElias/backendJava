package br.cervejariaSC.expedbeer.usuario.cliente;

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
@RequestMapping("/cliente")
@AllArgsConstructor
@CrossOrigin("*")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/")
    public ResponseEntity<ClienteRepresentation.Detail> criar(@Valid @RequestBody ClienteRepresentation.CreateUsuario create) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ClienteRepresentation.Detail.from(this.clienteService.salvar(create)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteRepresentation.Detail> atualizar(@PathVariable("id") Long id, @Valid @RequestBody ClienteRepresentation.UpdateUsuario update) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ClienteRepresentation.Detail.from(this.clienteService.atualizar(id, update)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteRepresentation.Detail> buscaUmUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ClienteRepresentation.Detail.from(this.usuarioService.getUsuarioById(id)));
    }

    @GetMapping("/")
    public ResponseEntity<Paginacao> buscaComFiltro(
            @QuerydslPredicate(root = Usuario.class) Predicate filtroURI,
            @RequestParam(name = "tamanhoPagina", defaultValue = "10") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0") int pagina,
            @RequestParam(name = "cliente", defaultValue = "") String cliente) {

        BooleanExpression filtro = QUsuario.usuario.tipo.eq(Usuario.Tipo.Cliente);

        if (!Objects.isNull(filtroURI)) {
            filtro = filtro.and(filtroURI);
        }

        if (!cliente.equals("")) {
            filtro = filtro.and(QUsuario.usuario.razao.containsIgnoreCase(cliente));
        }

        Pageable paginaDesejada = PageRequest.of(pagina, tamanhoPagina);

        Page<Usuario> listaCliente = this.usuarioRepository.findAll(filtro, paginaDesejada);

        List<ClienteRepresentation.Lista> clientes = ClienteRepresentation.Lista.from(listaCliente.getContent());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Paginacao.builder()
                        .paginaSelecionada(pagina)
                        .tamanhoPagina(tamanhoPagina)
                        .proximaPagina(listaCliente.hasNext())
                        .conteudo(clientes)
                        .build());
    }
}
