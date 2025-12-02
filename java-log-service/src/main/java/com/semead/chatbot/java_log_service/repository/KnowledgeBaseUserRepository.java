package com.semead.chatbot.java_log_service.repository;

import com.semead.chatbot.java_log_service.model.KnowledgeBaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeBaseUserRepository extends JpaRepository<KnowledgeBaseUser, Long> {
}
