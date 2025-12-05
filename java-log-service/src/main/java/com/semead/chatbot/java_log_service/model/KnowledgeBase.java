package com.semead.chatbot.java_log_service.model;

import com.semead.chatbot.java_log_service.model.KnowledgeBaseUser;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "knowledgebase")
public class KnowledgeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String pergunta;
    private String resposta;
    private String categoria;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "atualizado_por", referencedColumnName = "id")
    private KnowledgeBaseUser updatedBy;
}
