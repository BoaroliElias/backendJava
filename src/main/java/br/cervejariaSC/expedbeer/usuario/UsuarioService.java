package br.cervejariaSC.expedbeer.usuario;

import br.cervejariaSC.expedbeer.exception.IncorrectPasswordException;
import br.cervejariaSC.expedbeer.exception.NotFoundException;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public Usuario getUsuarioById(Long id) {
        BooleanExpression filter =
                QUsuario.usuario.id.eq(id);
        return this.usuarioRepository.findOne(filter)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
    }

    public UsuarioRepresentation.Logged login(UsuarioRepresentation.Login login) {
        BooleanExpression filter =
                QUsuario.usuario.login.eq(login.getLogin());
        Usuario usuario = this.usuarioRepository.findOne(filter).orElseThrow(() -> new NotFoundException("Login incorreto."));
        if (usuario.isAtivo()) {
            if (usuario.getSenha().equals(login.getSenha())) {
                String token = "";
                return UsuarioRepresentation.Logged.from(usuario, token);
            } else {
                throw new IncorrectPasswordException("Senha incorreta");
            }
        } else {
            throw new IncorrectPasswordException("Usuário inativo");
        }
    }
}
