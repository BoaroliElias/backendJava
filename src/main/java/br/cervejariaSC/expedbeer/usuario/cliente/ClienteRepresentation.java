package br.cervejariaSC.expedbeer.usuario.cliente;

import br.cervejariaSC.expedbeer.usuario.Usuario;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public interface ClienteRepresentation {

    @Data
    @Getter
    @Setter
    class CreateUsuario{
        @NotNull(message = "O campo razão social não pode ser nulo")
        @Size(max = 200, min = 1, message = "A razão social deve conter entre 1 e 200 caracteres")
        private String razao;

        @NotNull(message = "O campo login não pode ser nulo")
        @Size(max = 50, min = 4, message = "O login deve conter entre 4 e 50 caracteres")
        private String login;

        @NotNull(message = "O campo senha não pode ser nulo")
        @Size(max = 50, min = 4, message = "A senha deve conter entre 4 e 50 caracteres")
        private String senha;

        @NotNull(message = "O campo rua não pode ser nulo")
        @Size(max = 200, min = 1, message = "O campo rua deve conter entre 1 e 200 caracteres")
        private String rua;

        @NotNull(message = "O campo número não pode ser nulo")
        @Size(max = 10, min = 1, message = "O campo número deve conter entre 1 e 10 caracteres")
        private String numero;

        @NotNull(message = "O campo cidade não pode ser nulo")
        @Size(max = 200, min = 1, message = "O campo cidade deve conter entre 1 e 200 caracteres")
        private String cidade;

        @NotNull(message = "O campo cep não pode ser nulo")
        private Integer cep;

        @NotNull(message = "O campo UF não pode ser nulo")
        @Size(max = 2, min = 2, message = "O campo UF deve ter 2 caracteres")
        private String uf;
    }

    @Data
    @Getter
    @Setter
    class UpdateUsuario{
        @NotNull(message = "O campo razão social não pode ser nulo")
        @Size(max = 200, min = 1, message = "A razão social deve conter entre 1 e 200 caracteres")
        private String razao;

        @NotNull(message = "O campo senha não pode ser nulo")
        @Size(max = 50, min = 4, message = "A senha deve conter entre 4 e 50 caracteres")
        private String senha;

        @NotNull(message = "O campo rua não pode ser nulo")
        @Size(max = 200, min = 1, message = "O campo rua deve conter entre 1 e 200 caracteres")
        private String rua;

        @NotNull(message = "O campo número não pode ser nulo")
        @Size(max = 10, min = 1, message = "O campo número deve conter entre 1 e 10 caracteres")
        private String numero;

        @NotNull(message = "O campo cidade não pode ser nulo")
        @Size(max = 200, min = 1, message = "O campo cidade deve conter entre 1 e 200 caracteres")
        private String cidade;

        @NotNull(message = "O campo cep não pode ser nulo")
        private Integer cep;

        @NotNull(message = "O campo UF não pode ser nulo")
        @Size(max = 2, min = 2, message = "O campo UF deve ter 2 caracteres")
        private String uf;
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detail {
        private Long id;
        private Usuario.Tipo tipo;
        private String razao;
        private String login;
        private String senha;
        private String rua;
        private String numero;
        private String cidade;
        private Integer cep;
        private String uf;

        public static Detail from(Usuario usuario) {
            return Detail.builder()
                    .id(usuario.getId())
                    .razao(usuario.getRazao())
                    .login(usuario.getLogin())
                    .senha(usuario.getSenha())
                    .tipo(usuario.getTipo())
                    .cep(usuario.getCep())
                    .cidade(usuario.getCidade())
                    .numero(usuario.getNumero())
                    .rua(usuario.getRua())
                    .uf(usuario.getUf())
                    .build();
        }
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Lista {
        private Long id;
        private String razao;
        private String cidade;
        private String rua;


        public static Lista from(Usuario usuario) {
            return Lista.builder()
                    .id(usuario.getId())
                    .razao(usuario.getRazao())
                    .cidade(usuario.getCidade())
                    .rua(usuario.getRua())
                    .build();
        }

        public static List<Lista> from(List<Usuario> usuarios) {
            return usuarios.stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
