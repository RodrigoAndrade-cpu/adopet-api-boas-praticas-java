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
class VerificaSePetTemAdocaoEmAndamentoTest {

    @InjectMocks
    private VerificaSePetTemAdocaoEmAndamento verifica;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoEmUmPetComSolicitacaoEmAndamento() {

        //ARRANGE
        BDDMockito.given(adocaoRepository.existsByPetIdAndStatus(
                dto.petId(),
                StatusAdocao.AGUARDANDO_AVALIACAO)
        ).willReturn(true);

        //ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class, () -> verifica.verificar(dto));
    }

    @Test
    void deveriaPermitirSolicitacaoDeAdocaoEmUmPetSemSolicitacao() {

        //ARRANGE
        BDDMockito.given(adocaoRepository.existsByPetIdAndStatus(
                dto.petId(),
                StatusAdocao.AGUARDANDO_AVALIACAO)
        ).willReturn(false);

        //ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> verifica.verificar(dto));
    }

}