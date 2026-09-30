package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.abrigoDto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.dto.petDto.CadastrarPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculadoraProbabilidadeAdocaoTest {

    @Test
    void deveriaRetornarProbabilidadeAltaParaPetComIdadeBaixaEPesoBaixo() {
        //idade 4 anos peso 4kg - ALTA

        //ARRANGE
        Abrigo abrigo = new Abrigo(new CadastrarAbrigoDto(
                "Abrigo feliz",
                "74446604356",
                "abrigofeliz@email.com.br"
        ));
        Pet pet = new Pet(new CadastrarPetDto(
                TipoPet.GATO,
                "Miau",
                "Siames",
                4,
                "Cinza",
                4.0f
        ), abrigo);
        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();

        //ACT
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        //ASSERT
        Assertions.assertEquals(ProbabilidadeAdocao.ALTA, probabilidade);
    }

    @Test
    void deveriaRetornarProbabilidadeMediaParaPetComIdadeAltaEPesoBaixo() {
        //idade 15 anos peso 4kg - MEDIA

        Abrigo abrigo = new Abrigo(new CadastrarAbrigoDto(
                "Abrigo feliz",
                "74446604356",
                "abrigofeliz@email.com.br"
        ));
        Pet pet = new Pet(new CadastrarPetDto(
                TipoPet.GATO,
                "Miau",
                "Siames",
                15,
                "Cinza",
                4.0f
        ), abrigo);

        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        Assertions.assertEquals(ProbabilidadeAdocao.MEDIA, probabilidade);
    }

    @Test
    void deveriaRetornarProbabilidadeBaixaParaPetComIdadeAltaEPesoAlto() {
        //idade 15 anos peso 11kg - BAIXA

        Abrigo abrigo = new Abrigo(new CadastrarAbrigoDto(
                "Abrigo feliz",
                "74446604356",
                "abrigofeliz@email.com.br"
        ));
        Pet pet = new Pet(new CadastrarPetDto(
                TipoPet.GATO,
                "Miau",
                "Siames",
                15,
                "Cinza",
                11.0f
        ), abrigo);

        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        Assertions.assertEquals(ProbabilidadeAdocao.BAIXA, probabilidade);
    }

}