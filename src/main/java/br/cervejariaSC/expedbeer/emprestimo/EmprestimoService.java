package br.cervejariaSC.expedbeer.emprestimo;

import br.cervejariaSC.expedbeer.exception.NotFoundException;
import br.cervejariaSC.expedbeer.usuario.Usuario;
import br.cervejariaSC.expedbeer.vasilhame.Vasilhame;
import br.cervejariaSC.expedbeer.vasilhame.VasilhameService;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmprestimoService {

    private EmprestimoRepository emprestimoRepository;
    private VasilhameService vasilhameService;

    private static Emprestimo from(Vasilhame vasilhame, Usuario usuario) {
        return Emprestimo.builder()
                .vasilhame(vasilhame)
                .usuario(usuario)
                .dataInicial(LocalDate.now())
                .status(Emprestimo.Status.Emprestado)
                .build();
    }

    public List<Emprestimo> criar(Usuario usuario, List<Vasilhame> vasilhames) {
        return vasilhames.stream().map(vasilhameService::setEmprestado)
                .map(vasilhame -> {return from(vasilhame, usuario);})
                .map(emprestimoRepository::save).collect(Collectors.toList());
    }

    public Emprestimo buscaUm(Long id) {
        BooleanExpression filter = QEmprestimo.emprestimo.id.eq(id);
        return this.emprestimoRepository.findOne(filter)
                .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado"));
    }

    public Emprestimo buscaUmEmprestado(Long id) {
        Emprestimo emprestimo = buscaUm(id);
        if (emprestimo.getStatus().equals(Emprestimo.Status.Emprestado)) {
            return emprestimo;
        } else {
            throw new NotFoundException("Empréstimo não esta como emprestado");
        }
    }

    public Emprestimo buscaUmAguardandoColeta(Long id) {
        Emprestimo emprestimo = buscaUm(id);
        if (emprestimo.getStatus().equals(Emprestimo.Status.AguardandoColeta)) {
            return emprestimo;
        } else {
            throw new NotFoundException("Empréstimo não esta como aguardando coleta");
        }
    }

    public Emprestimo setEmprestado(Long id) {
        Emprestimo emprestimo = buscaUmAguardandoColeta(id);

        vasilhameService.setEmprestado(emprestimo.getVasilhame());
        emprestimo.setStatus(Emprestimo.Status.Emprestado);

        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo setAguardandoColeta(Long id) {
        Emprestimo emprestimo = buscaUmEmprestado(id);

        vasilhameService.setAguardandoColeta(emprestimo.getVasilhame());
        emprestimo.setStatus(Emprestimo.Status.AguardandoColeta);
        emprestimo.setDataAguardandoColeta(LocalDate.now());

        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo setDevolvido(Long id) {
        Emprestimo emprestimo = buscaUmAguardandoColeta(id);

        vasilhameService.setDisponivel(emprestimo.getVasilhame());
        emprestimo.setStatus(Emprestimo.Status.Devolvido);
        emprestimo.setDataDevolucao(LocalDate.now());

        return emprestimoRepository.save(emprestimo);
    }

    public List<Emprestimo> buscaEmprestimoNoCliente(String nomeCliente) {
        BooleanExpression filtro = QEmprestimo.emprestimo.usuario.razao.containsIgnoreCase(nomeCliente)
                .and(QEmprestimo.emprestimo.status.ne(Emprestimo.Status.Devolvido));
        return this.emprestimoRepository.findAll(filtro);
    }
}
