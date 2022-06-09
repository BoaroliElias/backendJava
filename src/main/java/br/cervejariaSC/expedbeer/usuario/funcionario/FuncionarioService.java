package br.cervejariaSC.expedbeer.usuario.funcionario;

import br.cervejariaSC.expedbeer.exception.BadRequestException;
import br.cervejariaSC.expedbeer.exception.LoginAlreadyExistsException;
import br.cervejariaSC.expedbeer.usuario.QUsuario;
import br.cervejariaSC.expedbeer.usuario.Usuario;
import br.cervejariaSC.expedbeer.usuario.UsuarioRepository;
import br.cervejariaSC.expedbeer.usuario.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FuncionarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public Usuario salvar(FuncionarioRepresentation.CreateUsuario create) {

        Optional<Usuario> usuario = this.usuarioRepository.findOne(QUsuario.usuario.login.eq(create.getLogin()));

        if (usuario.isEmpty()) {
            return this.usuarioRepository.save(Usuario.builder()
                    .login(create.getLogin())
                    .senha(create.getSenha())
                    .ativo(true)
                    .tipo(Usuario.Tipo.Funcionario)
                    .build());
        }
        throw new LoginAlreadyExistsException("O login já está sendo usado");
    }

    public Usuario atualizar(Long id, FuncionarioRepresentation.UpdateUsuario update) {

        Usuario usuario = usuarioService.getUsuarioById(id);
        if (!usuario.getTipo().equals(Usuario.Tipo.Funcionario)) {
            throw new BadRequestException("Usuário fornecido não é do tipo Funcionario!");
        }

        usuario.setSenha(update.getSenha());

        return this.usuarioRepository.save(usuario);
    }

    public Usuario inativar(Long id) {

        Usuario usuario = usuarioService.getUsuarioById(id);
        if (!usuario.getTipo().equals(Usuario.Tipo.Funcionario)) {
            throw new BadRequestException("Usuário fornecido não é do tipo Funcionario!");
        }

        usuario.setAtivo(false);

        return this.usuarioRepository.save(usuario);
    }

    public Usuario ativar(Long id) {

        Usuario usuario = usuarioService.getUsuarioById(id);
        if (!usuario.getTipo().equals(Usuario.Tipo.Funcionario)) {
            throw new BadRequestException("Usuário fornecido não é do tipo Funcionario!");
        }

        usuario.setAtivo(true);

        return this.usuarioRepository.save(usuario);
    }
}
