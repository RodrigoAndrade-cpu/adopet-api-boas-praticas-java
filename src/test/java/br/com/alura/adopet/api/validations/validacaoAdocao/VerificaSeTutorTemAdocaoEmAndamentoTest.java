package br.com.alura.adopet.api.validations.validacaoAdocao;


import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exceptions.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class VerificaSeTutorTemAdocaoEmAndamentoTest {

    @InjectMocks
    private VerificaSeTutorTemAdocaoEmAndamento verifica;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoSeOTutorEstiverComAdocaoEmAndamento() {
        // Arrange
        BDDMockito.given(dto.tutorId()).willReturn(1L);
        BDDMockito.given(adocaoRepository.existsByTutorIdAndStatus(
                dto.tutorId(),
                StatusAdocao.AGUARDANDO_AVALIACAO)
        ).willReturn(true);

        // Act + Assert
        Assertions.assertThrows(ValidacaoException.class, () -> verifica.verificar(dto));
    }

    @Test
    void deveriaPermitirSolicitacaoDeAdocaoSeOTutorNaoEstiverComAdocaoEmAndamento() {
        // Arrange
        BDDMockito.given(dto.tutorId()).willReturn(1L);
        BDDMockito.given(adocaoRepository.existsByTutorIdAndStatus(
                dto.tutorId(),
                StatusAdocao.AGUARDANDO_AVALIACAO)
        ).willReturn(false);

        // Act + Assert
        Assertions.assertDoesNotThrow(() -> verifica.verificar(dto));
    }
}