package br.cervejariaSC.expedbeer.usuario;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface UsuarioRepresentation {

    @Data
    @Getter
    @Setter
    @Builder
    class Detalhe {
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

        public static Detalhe from(Usuario usuario) {
            return  Detalhe.builder()
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
    class Login {

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
    @Builder
    class Logged {
        private Detalhe usuario;
        private String token;

        public static Logged from(Usuario usuario, String token) {
            return Logged.builder()
                    .usuario(Detalhe.from(usuario))
                    .token(token)
                    .build();
        }
    }
}
