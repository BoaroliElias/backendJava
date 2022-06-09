package br.cervejariaSC.expedbeer.usuario.cliente;

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
public class ClienteService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public Usuario salvar(ClienteRepresentation.CreateUsuario create) {

        Optional<Usuario> usuario = this.usuarioRepository.findOne(QUsuario.usuario.login.eq(create.getLogin()));

        if (usuario.isEmpty()) {
            return this.usuarioRepository.save(Usuario.builder()
                    .razao(create.getRazao())
                    .login(create.getLogin())
                    .senha(create.getSenha())
                    .ativo(true)
                    .tipo(Usuario.Tipo.Cliente)
                    .cep(create.getCep())
                    .cidade(create.getCidade())
                    .numero(create.getNumero())
                    .rua(create.getRua())
                    .uf(create.getUf())
                    .build());
        }
        throw new LoginAlreadyExistsException("O login já está sendo usado");
    }

    public Usuario atualizar(Long id, ClienteRepresentation.UpdateUsuario update) {

        Usuario usuario = usuarioService.getUsuarioById(id);
        if (!usuario.getTipo().equals(Usuario.Tipo.Cliente)) {
            throw new BadRequestException("Usuário fornecido não é do tipo Cliente!");
        }

        usuario.setRazao(update.getRazao());
        usuario.setSenha(update.getSenha());
        usuario.setCep(update.getCep());
        usuario.setCidade(update.getCidade());
        usuario.setNumero(update.getNumero());
        usuario.setRua(update.getRua());
        usuario.setUf(update.getUf());

        return this.usuarioRepository.save(usuario);
    }   
}
