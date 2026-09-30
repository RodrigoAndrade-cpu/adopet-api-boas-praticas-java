package br.com.alura.adopet.api.validations.validacaoAdocao;

import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exceptions.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerificaSePetTemAdocaoEmAndamento implements ValidacaoSolicitacaoDeAdocao {

    @Autowired
    private AdocaoRepository adocaoRepository;


    public void verificar(SolicitacaoAdocaoDto dto) {
        boolean petTemAdocaoEmAndamento = adocaoRepository
                .existsByPetIdAndStatus(dto.petId(), StatusAdocao.AGUARDANDO_AVALIACAO);
        if (petTemAdocaoEmAndamento) {
            throw new ValidacaoException("Pet já está aguardando avaliação para ser adotado!");
        }

    }
}
