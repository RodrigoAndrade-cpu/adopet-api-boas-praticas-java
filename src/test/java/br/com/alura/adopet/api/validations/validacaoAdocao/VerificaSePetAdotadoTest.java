package br.com.alura.adopet.api.validations.validacaoAdocao;

import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exceptions.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VerificaSePetAdotadoTest {

    @InjectMocks
    private VerificaSePetAdotado verifica;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Pet pet;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    void deveriaPermitirASolicitacaoSeOPetNaoEstiverAdotado() {

        //ARRANGE
        BDDMockito.given(petRepository.getReferenceById(dto.petId())).willReturn(pet);
        BDDMockito.given(pet.getAdotado()).willReturn(false);

        //ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> verifica.verificar(dto));
    }

    @Test
    void NaoDeveriaPermitirASolicitacaoSeOPetEAdotado() {

        //ARRANGE
        BDDMockito.given(petRepository.getReferenceById(dto.petId())).willReturn(pet);
        BDDMockito.given(pet.getAdotado()).willReturn(true);

        //ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class,() -> verifica.verificar(dto));
    }

}