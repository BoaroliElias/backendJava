package br.cervejariaSC.expedbeer.usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;

    @NotNull(message = "O tipo do usuário não pode ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO")
    private Tipo tipo;

    @Size(max = 200, min = 1, message = "A razão social deve conter entre 1 e 200 caracteres")
    @Column(name = "RAZAO_SOCIAL")
    private String razao;

    @NotNull(message = "O campo login não pode ser nulo")
    @Size(max = 50, min = 4, message = "O login deve conter entre 4 e 50 caracteres")
    @Column(name = "LOGIN")
    private String login;

    @NotNull(message = "O campo senha não pode ser nulo")
    @Size(max = 50, min = 4, message = "A senha deve conter entre 4 e 50 caracteres")
    @Column(name = "SENHA")
    @Convert(converter = SenhaConverter.class)
    private String senha;

    @Column(name = "ATIVO")
    private boolean ativo;

    @Size(max = 200, min = 1, message = "O campo rua deve conter entre 1 e 200 caracteres")
    @Column(name = "RUA")
    private String rua;

    @Size(max = 10, min = 1, message = "O campo número deve conter entre 1 e 10 caracteres")
    @Column(name = "NUMERO")
    private String numero;

    @Size(max = 200, min = 1, message = "O campo cidade deve conter entre 1 e 200 caracteres")
    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "CEP")
    private Integer cep;

    @Size(max = 2, min = 2, message = "O campo UF deve ter 2 caracteres")
    @Column(name = "UF")
    private String uf;

    public enum Tipo{
        Administrador,
        Cliente,
        Funcionario
    }
}
