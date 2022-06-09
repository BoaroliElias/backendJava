package br.cervejariaSC.expedbeer.tipoVasilhame;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tipo_vasilhame")
@AllArgsConstructor
@CrossOrigin("*")
public class TipoVasilhameController {

    private final TipoVasilhameService tipoVasilhameService;

    private final TipoVasilhameRepository tipoVasilhameRepository;

    @PostMapping("/")
    public ResponseEntity<TipoVasilhameRepresentation.Detalhes> cadastrar(
            @Valid @RequestBody TipoVasilhameRepresentation.CreateOrUpdateTipoVasilhame create) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TipoVasilhameRepresentation.Detalhes.from(this.tipoVasilhameService.criar(create)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoVasilhameRepresentation.Detalhes> alterar(
            @PathVariable("id") Long id,
            @Valid @RequestBody TipoVasilhameRepresentation.CreateOrUpdateTipoVasilhame update) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(TipoVasilhameRepresentation.Detalhes.from(this.tipoVasilhameService.alterar(id, update)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoVasilhameRepresentation.Detalhes> buscarUm(
            @PathVariable("id") Long id){

        return ResponseEntity.ok(TipoVasilhameRepresentation.Detalhes
                .from(this.tipoVasilhameService.buscarUm(id)));
    }

    @GetMapping("/")
    public ResponseEntity<List<TipoVasilhameRepresentation.Lista>> buscarTodos() {

        List<TipoVasilhame> todos = this.tipoVasilhameRepository.findAll();

        return ResponseEntity.ok(TipoVasilhameRepresentation.Lista.from(todos));
    }
}

