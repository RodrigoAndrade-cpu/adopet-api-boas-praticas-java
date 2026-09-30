package br.com.alura.adopet.api.validations.validacaoAdocao;

import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exceptions.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerificaSePetAdotado implements ValidacaoSolicitacaoDeAdocao {

    @Autowired
    private PetRepository petRepository;

    public void verificar(SolicitacaoAdocaoDto dto) {
        Pet pet = petRepository.getReferenceById(dto.petId());
        if (pet.getAdotado()) {
            throw new ValidacaoException("Pet já foi adotado!");
        }
    }
}
