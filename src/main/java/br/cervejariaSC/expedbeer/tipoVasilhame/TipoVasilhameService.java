package br.cervejariaSC.expedbeer.tipoVasilhame;

import br.cervejariaSC.expedbeer.exception.NotFoundException;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TipoVasilhameService {

    private TipoVasilhameRepository tipoVasilhameRepository;

    public TipoVasilhame criar(TipoVasilhameRepresentation.CreateOrUpdateTipoVasilhame create) {


        return this.tipoVasilhameRepository.save(TipoVasilhame.builder()
                .descricao(create.getDescricao())
                .volume(create.getVolume())
                .build());
    }
    public TipoVasilhame alterar(Long id, TipoVasilhameRepresentation.CreateOrUpdateTipoVasilhame update) {

        TipoVasilhame tipoVasilhame = this.buscarUm(id);
        tipoVasilhame.setDescricao(update.getDescricao());
        tipoVasilhame.setVolume(update.getVolume());

        return this.tipoVasilhameRepository.save(tipoVasilhame);
    }

    public TipoVasilhame buscarUm(Long id) {
        BooleanExpression filter =
                QTipoVasilhame.tipoVasilhame.id.eq(id);
        return this.tipoVasilhameRepository.findOne(filter)
                .orElseThrow(() -> new NotFoundException("Tipo de Vasilhame não encontrado"));
    }
}
