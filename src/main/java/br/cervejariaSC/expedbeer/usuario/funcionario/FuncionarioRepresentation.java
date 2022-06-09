package br.cervejariaSC.expedbeer.usuario.funcionario;

import br.cervejariaSC.expedbeer.usuario.Usuario;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public interface FuncionarioRepresentation {

    @Data
    @Getter
    @Setter
    class CreateUsuario{
        @NotNull(message = "O campo login não pode ser nulo")
        @Size(max = 50, min = 4, message = "O login deve conter entre 4 e 50 caracteres")
        private String login;

        @NotNull(message = "O campo senha não pode ser nulo")
        @Size(max = 50, min = 4, message = "A senha deve conter entre 4 e 50 caracteres")
        private String senha;
    }

    @Data
    @Getter
    @Setter
    class UpdateUsuario{
        @NotNull(message = "O campo senha não pode ser nulo")
        @Size(max = 50, min = 4, message = "A senha deve conter entre 4 e 50 caracteres")
        private String senha;
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detail {
        private Long id;
        private Usuario.Tipo tipo;
        private String login;
        private String senha;
        private boolean ativo;

        public static Detail from(Usuario usuario) {
            return Detail.builder()
                    .id(usuario.getId())
                    .login(usuario.getLogin())
                    .senha(usuario.getSenha())
                    .tipo(usuario.getTipo())
                    .ativo(usuario.isAtivo())
                    .build();
        }
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Lista {
        private Long id;
        private String login;
        private boolean ativo;

        public static Lista from(Usuario usuario) {
            return Lista.builder()
                    .id(usuario.getId())
                    .login(usuario.getLogin())
                    .ativo(usuario.isAtivo())
                    .build();
        }

        public static List<Lista> from(List<Usuario> usuarios) {
            return usuarios.stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
