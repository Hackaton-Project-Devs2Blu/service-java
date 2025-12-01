package com.semead.chatbot.java_log_service.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class KnowledgeBaseResponseDto {

    private Long id;
    private String titulo;
    private String pergunta;
    private String resposta;
    private String categoria;
    private LocalDateTime updatedAt;
    private String updatedByName;
}
