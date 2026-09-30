package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.exceptions.ValidacaoException;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {

    @MockitoBean
    private AbrigoService abrigoService;

    @MockitoBean
    private PetService petService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeListarAbrigos() throws Exception {
        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos")
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeCadastrarAbrigos() throws Exception {

        //Arrange
        String json = """
                {
                    "nome": "Abrigo Legal",
                    "telefone": "9845306726",
                    "email": "email@abrigo.com"
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoDeCadastrarAbrigos() throws Exception {

        //Arrange
        String json = """
                {
                    "nome": "Abrigo Legal",
                    "email": "email@abrigo.com"
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeListarPetsDoAbrigoPeloNome() throws Exception {

        //Arrange
        String nome = "Abrigo Legal";

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos/{nome}/pets", nome)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeListarPetsDoAbrigoPeloId() throws Exception {

        //Arrange
        String id = "1";

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos/{id}/pets", id)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoDeListarPetsDoAbrigoPorIdInvalido() throws Exception {
        //Arrange
        String id = "1";
        BDDMockito.given(abrigoService.listarPetsDoAbrigo(id)).willThrow(ValidacaoException.class);

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos/{id}/pets", id)
        ).andReturn().getResponse();

        //Assert
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoDeListarPetsDoAbrigoPorNomeInvalido() throws Exception {
        //Arrange
        String nome = "Miau";
        BDDMockito.given(abrigoService.listarPetsDoAbrigo(nome)).willThrow(ValidacaoException.class);

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos/{nome}/pets", nome)
        ).andReturn().getResponse();

        //Assert
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeCadastrarPetsPeloId() throws Exception {

        //Arrange
        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Miau",
                    "raca": "padrao",
                    "idade": "5",
                    "cor" : "Azul",
                    "peso": "7.5"
                }
                """;

        String abrigoId = "1";

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos/{abrigoId}/pets", abrigoId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo404ParaRequisicaoDeCadastrarPetPeloIdInvalido() throws Exception {

        //Arrange
        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Miau",
                    "raca": "padrao",
                    "idade": "5",
                    "cor": "Azul",
                    "peso": "7.5"
                }
                """;

        String abrigoId = "1";

        BDDMockito.given(abrigoService.carregarAbrigo(abrigoId)).willThrow(ValidacaoException.class);

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos/{abrigoId}/pets", abrigoId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeCadastrarPetsPeloNome() throws Exception {

        //Arrange
        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Miau",
                    "raca": "padrao",
                    "idade": "5",
                    "cor" : "Azul",
                    "peso": "7.5"
                }
                """;

        String abrigoNome = "Abrigo Legal";

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos/{abrigoNome}/pets", abrigoNome)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo404ParaRequisicaoDeCadastrarPetsPeloNome() throws Exception {

        //Arrange
        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Miau",
                    "raca": "padrao",
                    "idade": "5",
                    "cor" : "Azul",
                    "peso": "7.5"
                }
                """;

        String abrigoNome = "Abrigo Legal";

        BDDMockito.given(abrigoService.carregarAbrigo(abrigoNome)).willThrow(ValidacaoException.class);

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos/{abrigoNome}/pets", abrigoNome)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(404, response.getStatus());
    }
}













