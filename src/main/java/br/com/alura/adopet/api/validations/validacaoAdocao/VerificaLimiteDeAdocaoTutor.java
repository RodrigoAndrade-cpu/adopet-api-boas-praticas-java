package br.com.alura.adopet.api.validations.validacaoAdocao;


import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exceptions.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerificaLimiteDeAdocaoTutor implements ValidacaoSolicitacaoDeAdocao {

    @Autowired
    private AdocaoRepository adocaoRepository;

    public void verificar(SolicitacaoAdocaoDto dto) {
        Integer adocoesTutor = adocaoRepository
                .countByTutorIdAndStatus(dto.tutorId(), StatusAdocao.APROVADO);

        if (adocoesTutor == 5) {
            throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
        }
    }
}
