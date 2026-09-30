package br.com.alura.adopet.api.validations.validacaoAdocao;

import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;

public interface ValidacaoSolicitacaoDeAdocao {

    void verificar(SolicitacaoAdocaoDto dto);
}
