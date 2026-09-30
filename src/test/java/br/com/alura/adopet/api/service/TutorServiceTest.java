package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.tutorDto.AtualizarTutorDto;
import br.com.alura.adopet.api.dto.tutorDto.CadastrarTutorDto;
import br.com.alura.adopet.api.exceptions.TutorJaExisteException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService tutorService;

    @Mock
    private CadastrarTutorDto dto;

    @Mock
    private AtualizarTutorDto atualizarDto;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private Tutor tutor;

    @Test
    void deveriaCadastrarUmTutor() {

        //ARRANGE
        BDDMockito.given(tutorRepository.existsByTelefoneOrEmail(
                dto.telefone(),
                dto.email())
        ).willReturn(false);

        //ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> tutorService.cadastrar(dto));
    }

    @Test
    void naoDeveriaCadastrarUmTutor() {

        //ARRANGE
        BDDMockito.given(tutorRepository.existsByTelefoneOrEmail(
                dto.telefone(),
                dto.email())
        ).willReturn(true);

        //ASSERT + ACT
        Assertions.assertThrows(TutorJaExisteException.class, () -> tutorService.cadastrar(dto));
    }

    @Test
    void deveriaAtualizarTutor() {

        //ARRANGE
        BDDMockito.given(tutorRepository.getReferenceById(
                atualizarDto.tutorId())).willReturn(tutor);

        //ACT
        tutorService.atualizar(atualizarDto);
        then(tutor).should().atualizarDados(atualizarDto);
    }
}










