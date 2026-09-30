package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.tutorDto.AtualizarTutorDto;
import br.com.alura.adopet.api.dto.tutorDto.CadastrarTutorDto;
import br.com.alura.adopet.api.exceptions.TutorJaExisteException;
import br.com.alura.adopet.api.exceptions.TutorNaoEncontradoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;


    public void cadastrar(CadastrarTutorDto dto) {
        boolean jaCadastrado = tutorRepository
                .existsByTelefoneOrEmail(dto.telefone(), dto.email());

        if (jaCadastrado) {
            throw new TutorJaExisteException("Dados já cadastrados para outro tutor");
        }
        tutorRepository.save(new Tutor(dto));
    }

    public void atualizar(AtualizarTutorDto dto) {
        Tutor tutor = tutorRepository.getReferenceById(dto.tutorId());
        tutor.atualizarDados(dto);
    }
}
