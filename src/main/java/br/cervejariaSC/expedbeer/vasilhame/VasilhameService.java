package br.cervejariaSC.expedbeer.vasilhame;

import br.cervejariaSC.expedbeer.exception.BadRequestException;
import br.cervejariaSC.expedbeer.exception.NotFoundException;
import br.cervejariaSC.expedbeer.tipoVasilhame.TipoVasilhame;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VasilhameService {

    private VasilhameRepository vasilhameRepository;

    public Vasilhame criar(TipoVasilhame tipoVasilhame){


        return this.vasilhameRepository.save(Vasilhame.builder()
                .tipoVasilhame(tipoVasilhame)
                .status(Vasilhame.Status.Disponivel)
                .build());
    }

    public Vasilhame alterar(Long id, TipoVasilhame tipoVasilhame){

        Vasilhame vasilhame = this.buscarUm(id);

        vasilhame.setTipoVasilhame(tipoVasilhame);

        return this.vasilhameRepository.save(vasilhame);
    }

    public Vasilhame buscarUm(Long id) {
        BooleanExpression filter = QVasilhame.vasilhame.id.eq(id);

        return this.vasilhameRepository.findOne(filter)
                .orElseThrow(() -> new NotFoundException("Vasilhame não encontrado"));
    }

    public Vasilhame buscarUmDisponivel(Long id) {
        BooleanExpression filter = QVasilhame.vasilhame.id.eq(id);
        Vasilhame vasilhame = this.vasilhameRepository.findOne(filter).orElseThrow(() -> new NotFoundException("Vasilhame não encontrado"));
        if (vasilhame.getStatus().equals(Vasilhame.Status.Disponivel)) {
            return vasilhame;
        } else {
            throw  new NotFoundException("Vasilhame precisa estar disponível para uso");
        }
    }

    public Vasilhame setEmprestado(Vasilhame vasilhame) {
        if (vasilhame.getStatus().equals(Vasilhame.Status.Disponivel)) {
            vasilhame.setStatus(Vasilhame.Status.Emprestado);

            return vasilhameRepository.save(vasilhame);
        } else {
            throw new BadRequestException("Vasilhame precisa estar disponível");
        }
    }

    public Vasilhame setAguardandoColeta(Vasilhame vasilhame) {
        if (vasilhame.getStatus().equals(Vasilhame.Status.Emprestado)) {
            vasilhame.setStatus(Vasilhame.Status.AguardandoColeta);

            return vasilhameRepository.save(vasilhame);
        } else {
            throw new BadRequestException("Vasilhame precisa estar emprestado");
        }
    }

    public Vasilhame setDisponivel(Vasilhame vasilhame) {
        if (vasilhame.getStatus().equals(Vasilhame.Status.AguardandoColeta)) {
            vasilhame.setStatus(Vasilhame.Status.Disponivel);

            return vasilhameRepository.save(vasilhame);
        } else {
            throw new BadRequestException("Vasilhame precisa estar com status aguardando coleta");
        }
    }

    public Vasilhame setDescartado(Vasilhame vasilhame) {
        if (vasilhame.getStatus().equals(Vasilhame.Status.Disponivel)) {
            vasilhame.setStatus(Vasilhame.Status.Descartado);

            return vasilhameRepository.save(vasilhame);
        } else {
            throw new BadRequestException("Vasilhame precisa estar disponível");
        }
    }

    public Vasilhame estornaDescarte(Vasilhame vasilhame) {
        if (vasilhame.getStatus().equals(Vasilhame.Status.Descartado)) {
            vasilhame.setStatus(Vasilhame.Status.Disponivel);

            return vasilhameRepository.save(vasilhame);
        } else {
            throw new BadRequestException("Vasilhame precisa estar descartado");
        }
    }
}
