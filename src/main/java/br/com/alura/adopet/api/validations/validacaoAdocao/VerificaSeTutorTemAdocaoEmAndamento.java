package br.com.alura.adopet.api.validations.validacaoAdocao;

import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exceptions.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerificaSeTutorTemAdocaoEmAndamento implements ValidacaoSolicitacaoDeAdocao {

    @Autowired
    private AdocaoRepository adocaoRepository;


    public void verificar(SolicitacaoAdocaoDto dto) {
        boolean existeAdocaoEmAndamento =
                adocaoRepository.existsByTutorIdAndStatus(
                        dto.tutorId(),
                        StatusAdocao.AGUARDANDO_AVALIACAO
                );

        if (existeAdocaoEmAndamento) {
            throw new ValidacaoException(
                    "Tutor já possui outra adoção aguardando avaliação!"
            );
        }
    }
}
