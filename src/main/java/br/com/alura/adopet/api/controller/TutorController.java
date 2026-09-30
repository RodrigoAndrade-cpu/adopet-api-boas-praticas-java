package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.tutorDto.AtualizarTutorDto;
import br.com.alura.adopet.api.dto.tutorDto.CadastrarTutorDto;
import br.com.alura.adopet.api.exceptions.TutorJaExisteException;
import br.com.alura.adopet.api.exceptions.TutorNaoEncontradoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorService tutorService;


    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastrarTutorDto dto) {
        try {
            tutorService.cadastrar(dto);
            return ResponseEntity.ok().body("Tutor cadastrado com sucesso!");
        } catch (TutorJaExisteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizarTutorDto dto) {
        try {
            tutorService.atualizar(dto);
            return ResponseEntity.ok().body("Tutor atualizado com sucesso!");
        } catch (TutorNaoEncontradoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
