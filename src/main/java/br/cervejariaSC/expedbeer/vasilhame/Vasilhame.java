package br.cervejariaSC.expedbeer.vasilhame;

import br.cervejariaSC.expedbeer.tipoVasilhame.TipoVasilhame;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "VASILHAME")
public class Vasilhame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VASILHAME")
    private Long id;

    @NotNull(message = "O tipo de vasilhame não pode ser nulo")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TipoVasilhame.class)
    @JoinColumn(name = "ID_TIPO_VASILHAME")
    private TipoVasilhame tipoVasilhame;

    @NotNull(message = "O status do vasilhame não pode ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    public enum Status{
        Disponivel,
        Emprestado,
        AguardandoColeta,
        Descartado
    }

}
