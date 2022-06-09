package br.cervejariaSC.expedbeer.usuario;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
@CrossOrigin("*")
public class UsuarioController {

    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<UsuarioRepresentation.Logged> login(@Valid @RequestBody UsuarioRepresentation.Login login) {
        return ResponseEntity.ok(this.usuarioService.login(login));
    }
}
