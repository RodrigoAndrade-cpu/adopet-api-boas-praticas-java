package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.abrigoDto.AbrigoDto;
import br.com.alura.adopet.api.dto.abrigoDto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.exceptions.AbrigoJaExisteException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService abrigoService;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private CadastrarAbrigoDto dto;

    @Mock
    private Abrigo abrigo;


    @Test
    void deveriaChamarAListaDeTodosOsAbrigos() {

        //ACT
        abrigoService.listar();

        //ASSERT
        then(abrigoRepository).should().findAll();
    }

    @Test
    void naoDeveriaCadastrarAbrigos() {

        //ARRANGE
        BDDMockito.given(abrigoRepository.existsByNomeOrTelefoneOrEmail(
                dto.nome(),
                dto.telefone(),
                dto.email())).willReturn(true);

        //ASSERT + ACT
        Assertions.assertThrows(AbrigoJaExisteException.class, () -> abrigoService.cadastrar(dto));
    }

    @Test
    void deveriaCadastrarAbrigos() {

        //ARRANGE
        BDDMockito.given(abrigoRepository.existsByNomeOrTelefoneOrEmail(
                dto.nome(),
                dto.telefone(),
                dto.email())).willReturn(false);

        //ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> abrigoService.cadastrar(dto));
    }

    @Test
    void deveriaListarTodosOsPetsDoAbrigo() {

        //ARRANGE
        String nome = "Miau";
        BDDMockito.given(abrigoRepository.findByNome(nome)).willReturn(Optional.of(abrigo));

        //ACT
        abrigoService.listarPetsDoAbrigo(nome);

        //ASSERT
        then(petRepository).should().findByAbrigo(abrigo);
    }
}

















