package br.cervejariaSC.expedbeer.emprestimo;

import br.cervejariaSC.expedbeer.usuario.Usuario;
import br.cervejariaSC.expedbeer.vasilhame.Vasilhame;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "EMPRESTIMO")
public class Emprestimo {
//testee
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPRESTIMO")
    private Long id;

    @NotNull(message = "O vasilhame do empréstimo não pode ser nulo")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Vasilhame.class)
    @JoinColumn(name = "ID_VASILHAME") //Fk de vasilhame na tabela de empréstimo
    private Vasilhame vasilhame;

    @NotNull(message = "O usuário de um empréstimo não pode ser nulo")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Usuario.class)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @NotNull(message = "A data inicial do empréstimo não pode ser nula")
    @Column(name = "DT_INICIAL")
    private LocalDate dataInicial;

    @Column(name = "DT_AGUARDANDO_COLETA")
    private LocalDate dataAguardandoColeta;

    @Column(name = "DT_DEVOLUCAO")
    private LocalDate dataDevolucao;

    @NotNull(message = "O status do empréstimo não pode ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    public enum Status{
        Emprestado,
        AguardandoColeta,
        Devolvido
    }


}
