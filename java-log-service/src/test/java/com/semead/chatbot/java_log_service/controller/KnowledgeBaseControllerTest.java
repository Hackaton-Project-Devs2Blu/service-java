package com.semead.chatbot.java_log_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semead.chatbot.java_log_service.dto.KnowledgeBaseRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class KnowledgeBaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser // Simula um usuário logado
    void shouldReturnOkWhenGetAllKnowledgeBaseItems() throws Exception {
        mockMvc.perform(get("/knowledgebase"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser // Simula um usuário logado
    void shouldCreateKnowledgeBaseItem() throws Exception {
        // Arrange
        KnowledgeBaseRequestDto requestDto = new KnowledgeBaseRequestDto();
        requestDto.setTitulo("Nova Pergunta de Teste");
        requestDto.setPergunta("Como faço para testar?");
        requestDto.setResposta("Escrevendo um teste de integração.");
        requestDto.setCategoria("Testes");
        requestDto.setUpdatedByUserId(1L);

        // Act & Assert
        mockMvc.perform(post("/knowledgebase")
                        .with(csrf()) // Adiciona um token CSRF válido
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Nova Pergunta de Teste"));
    }
}
