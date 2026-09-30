package br.com.alura.adopet.api.validations.validacaoAdocao;


import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exceptions.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VerificaLimiteDeAdocaoTutorTest {

    @InjectMocks
    private VerificaLimiteDeAdocaoTutor verifica;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    void naoDeveriaPermitirTutorFazerMaisAdocoesSeLimiteDe5FoiAtingido() {
        //ARRANGE
        BDDMockito.given(adocaoRepository.countByTutorIdAndStatus(
                dto.tutorId(),
                StatusAdocao.APROVADO)
        ).willReturn(5);

        //ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class, () -> verifica.verificar(dto));
    }

    @Test
    void deveriaPermitirTutorFazerMaisAdocoesSeLimiteForMenorQue5Foi() {
        //ARRANGE
        BDDMockito.given(adocaoRepository.countByTutorIdAndStatus(
                dto.tutorId(),
                StatusAdocao.APROVADO)
        ).willReturn(4);

        //ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> verifica.verificar(dto));
    }
}