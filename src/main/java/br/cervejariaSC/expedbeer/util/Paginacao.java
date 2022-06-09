package br.cervejariaSC.expedbeer.util;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Builder
public class Paginacao {

    private Integer tamanhoPagina;
    private Integer paginaSelecionada;
    private Boolean proximaPagina;
    private List<?> conteudo;
}