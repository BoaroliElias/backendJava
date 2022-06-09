package br.cervejariaSC.expedbeer.tipoVasilhame;

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
@Entity(name = "TIPO_VASILHAME")
public class TipoVasilhame {

    @Id
    @Column(name = "ID_TIPO_VASILHAME")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo descrição não pode ser nulo")
    @Size(max = 100, min = 1, message = "A descrição deve conter entre 1 e 100 caracteres")
    @Column(name = "DESCRICAO")
    private String descricao;

    @NotNull(message = "O campo Volume não pode ser nulo")
    @Column(name = "VOLUME")
    private int volume;

}
