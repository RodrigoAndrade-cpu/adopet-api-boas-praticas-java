package br.com.alura.adopet.api.dto.tutorDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AtualizarTutorDto(

        @NotNull
        Long tutorId,

        String nome,

        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
        String telefone,

        @Email
        String email
) {
}
