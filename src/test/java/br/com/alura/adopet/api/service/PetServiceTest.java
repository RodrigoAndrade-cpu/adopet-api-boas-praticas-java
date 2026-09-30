package br.com.alura.adopet.api.service;


import br.com.alura.adopet.api.dto.petDto.CadastrarPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService petService;

    @Mock
    private PetRepository petRepository;

    @Mock
    private CadastrarPetDto dto;

    @Mock
    private Abrigo abrigo;

    @Test
    void deveriaCadastrarUmPet() {

        //ACT
        petService.cadastrarPet(abrigo, dto);

        //ASSERT
        then(petRepository).should().save(new Pet(dto, abrigo));
    }

    @Test
    void deveriaListarTodosOsPetsDisponiveisParaAdocao() {

        //ACT
        petService.listarPetsDisponiveis();

        then(petRepository).should().findALlByAdocaoFalse();
    }
}













