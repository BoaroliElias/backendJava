package br.cervejariaSC.expedbeer.tipoVasilhame;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public interface TipoVasilhameRepresentation {

    @Data
    @Getter
    @Setter
    class CreateOrUpdateTipoVasilhame {

        @NotNull(message = "O campo descrição não pode ser nulo")
        @Size(max = 100, min = 1, message = "A descrição deve conter entre 1 e 100 caracteres")
        private String descricao;

        @NotNull(message = "O campo Volume não pode ser nulo")
        private int volume;
    }
    @Data
    @Getter
    @Setter
    @Builder
    class Detalhes {
        private Long id;
        private String descricao;
        private int volume;

        public static Detalhes from(TipoVasilhame tipoVasilhame) {
            return Detalhes.builder()
                    .id(tipoVasilhame.getId())
                    .descricao(tipoVasilhame.getDescricao())
                    .volume(tipoVasilhame.getVolume())
                    .build();
        }
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Lista {
        private Long id;
        private String descricao;

        private static Lista from(TipoVasilhame tipoVasilhame) {
            return Lista.builder()
                    .id(tipoVasilhame.getId())
                    .descricao(tipoVasilhame.getDescricao() + " - " + tipoVasilhame.getVolume() + " L ")
                    .build();
        }
        public static List<Lista> from (List<TipoVasilhame> tipoVasilhames) {
            return tipoVasilhames.stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
