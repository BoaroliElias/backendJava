package br.cervejariaSC.expedbeer.vasilhame;

import br.cervejariaSC.expedbeer.tipoVasilhame.TipoVasilhame;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public interface VasilhameRepresentation {

    @Data
    @Getter
    @Setter
    class CreateOrUpdateVasilhame {

        @NotNull(message = "O tipo de vasilhame não pode ser nulo")
        private Long tipoVasilhame;
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detalhes {
        private Long id;
        private TipoVasilhame tipoVasilhame;
        private Vasilhame.Status status;

        public static Detalhes from(Vasilhame vasilhame) {
            return Detalhes.builder()
                    .id(vasilhame.getId())
                    .tipoVasilhame(vasilhame.getTipoVasilhame())
                    .status(vasilhame.getStatus())
                    .build();
        }

        public static List<Detalhes> from(List<Vasilhame> vasilhames) {
            return vasilhames.stream()
                    .map(Detalhes::from)
                    .collect(Collectors.toList());
        }

    }


}
