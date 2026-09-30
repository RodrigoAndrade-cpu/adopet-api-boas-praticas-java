package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.adocaoDto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocaoDto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocaoDto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.*;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validations.validacaoAdocao.ValidacaoSolicitacaoDeAdocao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService adocaoService;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Mock
    private AprovacaoAdocaoDto aprovaDto;

    @Mock
    private ReprovacaoAdocaoDto reprovacaoDto;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;

    @Spy
    private Adocao adocao;

    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;

    @Mock
    private EmailService emailService;

    @Spy
    private List<ValidacaoSolicitacaoDeAdocao> validacoes = new ArrayList<>();

    @Mock
    private ValidacaoSolicitacaoDeAdocao validador1;

    @Mock
    private ValidacaoSolicitacaoDeAdocao validador2;

    @Test
    void deveriaSalvarSolicitacaoAoSolicitar() {

        //ARRANGE
        this.dto = new SolicitacaoAdocaoDto(6L, 3L, "motivo qualquer");
        BDDMockito.given(petRepository.getReferenceById(dto.petId())).willReturn(pet);
        BDDMockito.given(tutorRepository.getReferenceById(dto.tutorId())).willReturn(tutor);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);
        //ACT
        adocaoService.solicitar(dto);

        //ASSERT
        then(adocaoRepository).should().save(adocaoCaptor.capture());
        Adocao adocaoSalva = adocaoCaptor.getValue();
        Assertions.assertEquals(pet, adocaoSalva.getPet());
        Assertions.assertEquals(tutor, adocaoSalva.getTutor());
        Assertions.assertEquals(dto.motivo(), adocaoSalva.getMotivo());
    }

    @Test
    void deveriaChamarValidadoresDeAdocaoAoSolicitar() {
        //ARRANGE
        this.dto = new SolicitacaoAdocaoDto(10l, 20l, "motivo qualquer");
        BDDMockito.given(petRepository.getReferenceById(dto.petId())).willReturn(pet);
        BDDMockito.given(tutorRepository.getReferenceById(dto.tutorId())).willReturn(tutor);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);
        validacoes.add(validador1);
        validacoes.add(validador2);

        //ACT
        adocaoService.solicitar(dto);

        //ASSERT
        BDDMockito.then(validador1).should().verificar(dto);
        BDDMockito.then(validador2).should().verificar(dto);
    }

    @Test
    void deveriaEnviarEmailAoSolicitarAdocao() {

        //ARRANGE
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(10l, 30l, "motivo teste");
        BDDMockito.given(petRepository.getReferenceById(dto.petId())).willReturn(pet);
        BDDMockito.given(tutorRepository.getReferenceById(dto.tutorId())).willReturn(tutor);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);

        //ACT
        adocaoService.solicitar(dto);

        //ASSERT
        then(adocaoRepository).should().save(adocaoCaptor.capture());
        Adocao adocao = adocaoCaptor.getValue();
        then(emailService).should().enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Solicitação de adoção",
                "Olá " + adocao.getPet().getAbrigo().getNome() + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + adocao.getPet().getNome() + ". \nFavor avaliar para aprovação ou reprovação."
        );
    }

    @Test
    void deveriaAprovarSolicitacao() {

        //ARRANGE
        BDDMockito.given(adocaoRepository.getReferenceById(aprovaDto.adocaoId())).willReturn(adocao);
        BDDMockito.given(adocao.getPet()).willReturn(pet);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);
        BDDMockito.given(abrigo.getEmail()).willReturn("email@gmail.com");
        BDDMockito.given(adocao.getTutor()).willReturn(tutor);
        BDDMockito.given(tutor.getNome()).willReturn("Afonso");
        BDDMockito.given(adocao.getData()).willReturn(LocalDateTime.now());

        //ACT
        adocaoService.aprovar(aprovaDto);

        //ASSERT
        then(adocao).should().marcarComoAprovado();
        Assertions.assertEquals(StatusAdocao.APROVADO, adocao.getStatus());
    }

    @Test
    void deveriaReprovarSolicitacao() {

        //ARRANGE
        BDDMockito.given(adocaoRepository.getReferenceById(reprovacaoDto.adocaoId())).willReturn(adocao);
        BDDMockito.given(adocao.getPet()).willReturn(pet);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);
        BDDMockito.given(abrigo.getEmail()).willReturn("email@gmail.com");
        BDDMockito.given(adocao.getTutor()).willReturn(tutor);
        BDDMockito.given(tutor.getNome()).willReturn("Afonso");
        BDDMockito.given(adocao.getData()).willReturn(LocalDateTime.now());

        //ACT
        adocaoService.reprovar(reprovacaoDto);

        //ASSERT
        then(adocao).should().marcarComoReprovada(reprovacaoDto.justificativa());
        Assertions.assertEquals(StatusAdocao.REPROVADO, adocao.getStatus());
    }

    @Test
    void deveriaEnviarUmEmailAoAprovarSolicitacao() {

        //ARRANGE
        BDDMockito.given(adocaoRepository.getReferenceById(aprovaDto.adocaoId())).willReturn(adocao);
        BDDMockito.given(adocao.getPet()).willReturn(pet);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);
        BDDMockito.given(abrigo.getEmail()).willReturn("email@gmail.com");
        BDDMockito.given(adocao.getTutor()).willReturn(tutor);
        BDDMockito.given(tutor.getNome()).willReturn("Afonso");
        BDDMockito.given(adocao.getData()).willReturn(LocalDateTime.now());

        //ACT
        adocaoService.aprovar(aprovaDto);

        //ASSERT
        then(emailService).should().enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Adoção aprovada",
                "Parabéns " +adocao.getTutor().getNome() +"!\n\nSua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +" para agendar a busca do seu pet."
        );
    }

    @Test
    void deveriaEnviarUmEmailAoReprovarSolicitacao() {

        //ARRANGE
        BDDMockito.given(adocaoRepository.getReferenceById(aprovaDto.adocaoId())).willReturn(adocao);
        BDDMockito.given(adocao.getPet()).willReturn(pet);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);
        BDDMockito.given(abrigo.getEmail()).willReturn("email@gmail.com");
        BDDMockito.given(adocao.getTutor()).willReturn(tutor);
        BDDMockito.given(tutor.getNome()).willReturn("Afonso");
        BDDMockito.given(adocao.getData()).willReturn(LocalDateTime.now());

        //ACT
        adocaoService.reprovar(reprovacaoDto);

        //ASSERT
        then(emailService).should().enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Adoção reprovada",
                "Olá " + adocao.getTutor().getNome() + "!\n\nInfelizmente sua adoção do pet " + adocao.getPet().getNome() + ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi reprovada pelo abrigo " + adocao.getPet().getAbrigo().getNome() + " com a seguinte justificativa: " + adocao.getJustificativaStatus()
        );
    }
}













