package br.com.alura.adopet.api.dto.adocaoDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SolicitacaoAdocaoDto(

        @NotNull
        Long petId,

        @NotNull
        Long tutorId,

        @NotBlank
        String motivo
) {
}
