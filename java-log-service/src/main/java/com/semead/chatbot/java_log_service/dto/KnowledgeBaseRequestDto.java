package com.semead.chatbot.java_log_service.dto;

import lombok.Data;

@Data
public class KnowledgeBaseRequestDto {
    private String titulo;
    private String pergunta;
    private String resposta;
    private String categoria;
    private Long updatedByUserId;
}
