package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.petDto.CadastrarPetDto;
import br.com.alura.adopet.api.dto.petDto.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public List<PetDto> listarPetsDisponiveis() {
        return petRepository
                .findALlByAdocaoFalse()
                .stream()
                .map(PetDto::new)
                .toList();
    }

    public void cadastrarPet(Abrigo abrigo, CadastrarPetDto dto) {
        petRepository.save(new Pet(dto, abrigo));
    }
}
