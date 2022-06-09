package br.cervejariaSC.expedbeer.emprestimo;

import br.cervejariaSC.expedbeer.vasilhame.Vasilhame;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public interface EmprestimoRepresentation {

    @Data
    @Getter
    @Setter
    class Create {

        @NotNull(message = "O cliente de um empréstimo não pode ser nulo")
        private Long cliente;

        @NotNull(message = "O empréstimo deve ter ao menos um vasilhame")
        private List<Long> vasilhames;
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detalhes {
        private Long id;
        private Vasilhame vasilhame;
        private Long usuario;
        private LocalDate dataInicial;
        private LocalDate dataAguardandoColeta;
        private LocalDate dataDevolucao;
        private Emprestimo.Status status;

        public static Detalhes from(Emprestimo emprestimo) {
            return Detalhes.builder()
                    .id(emprestimo.getId())
                    .vasilhame(emprestimo.getVasilhame())
                    .usuario(emprestimo.getUsuario().getId())
                    .dataInicial(emprestimo.getDataInicial())
                    .dataAguardandoColeta(emprestimo.getDataAguardandoColeta())
                    .dataDevolucao(emprestimo.getDataDevolucao())
                    .status(emprestimo.getStatus())
                    .build();
        }

        public static List<Detalhes> from(List<Emprestimo> emprestimos) {
            return emprestimos.stream()
                    .map(Detalhes::from)
                    .collect(Collectors.toList());
        }
    }
}
