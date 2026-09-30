package br.com.alura.adopet.api.controller;


import br.com.alura.adopet.api.service.TutorService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class TutorControllerTest {

    @MockitoBean
    private TutorService tutorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoCadastrarTutor() throws Exception{

        //Arrange
        String json = """
                {
                    "nome": "Rogerio",
                    "telefone": "9384615303",
                    "email": "email@tutor.com.br"
                }
                """;

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoCadastrarTutorComDadosInvalidos() throws Exception{

        //Arrange
        String json = """
                {
                    "nome": "Rogerio",
                    "telefone": "93846153034444444444444",
                    "email": "email@tutor.com.br"
                }
                """;

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoAtualizarTutor() throws Exception{

        //Arrange
        String json = """
                {
                    "tutorId": "1",
                    "nome": "Rogerio",
                    "telefone": "9384615303",
                    "email": "email@tutor.com.br"
                }
                """;

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo404ParaRequisicaoAtualizarTutor() throws Exception{

        //Arrange
        String json = """
                {
                    "tutorId": "1",
                    "nome": "Rogerio",
                    "telefone": "9384615303333333333333333",
                    "email": "email@tutor.com.br"
                }
                """;

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        Assertions.assertEquals(400, response.getStatus());
    }
}









