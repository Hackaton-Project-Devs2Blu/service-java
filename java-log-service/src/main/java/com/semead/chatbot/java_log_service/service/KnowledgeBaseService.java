package com.semead.chatbot.java_log_service.service;

import com.semead.chatbot.java_log_service.dto.KnowledgeBaseRequestDto;
import com.semead.chatbot.java_log_service.dto.KnowledgeBaseResponseDto;
import com.semead.chatbot.java_log_service.model.KnowledgeBase;
import com.semead.chatbot.java_log_service.model.KnowledgeBaseUser;
import com.semead.chatbot.java_log_service.repository.KnowledgeBaseRepository;
import com.semead.chatbot.java_log_service.repository.KnowledgeBaseUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KnowledgeBaseService {

    @Autowired
    private KnowledgeBaseRepository knowledgeBaseRepository;

    @Autowired
    private KnowledgeBaseUserRepository userRepository;

    public List<KnowledgeBaseResponseDto> findAll() {
        return knowledgeBaseRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public KnowledgeBaseResponseDto findById(Long id) {
        return knowledgeBaseRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("KnowledgeBase item not found with id: " + id));
    }

    public KnowledgeBaseResponseDto create(KnowledgeBaseRequestDto requestDto) {
        KnowledgeBaseUser user = userRepository.findById(requestDto.getUpdatedByUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + requestDto.getUpdatedByUserId()));

        KnowledgeBase knowledgeBase = new KnowledgeBase();
        knowledgeBase.setTitulo(requestDto.getTitulo());
        knowledgeBase.setPergunta(requestDto.getPergunta());
        knowledgeBase.setResposta(requestDto.getResposta());
        knowledgeBase.setCategoria(requestDto.getCategoria());
        knowledgeBase.setUpdatedBy(user);

        KnowledgeBase saved = knowledgeBaseRepository.save(knowledgeBase);
        return convertToDto(saved);
    }

    public KnowledgeBaseResponseDto update(Long id, KnowledgeBaseRequestDto requestDto) {
        KnowledgeBase knowledgeBase = knowledgeBaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("KnowledgeBase item not found with id: " + id));

        KnowledgeBaseUser user = userRepository.findById(requestDto.getUpdatedByUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + requestDto.getUpdatedByUserId()));

        knowledgeBase.setTitulo(requestDto.getTitulo());
        knowledgeBase.setPergunta(requestDto.getPergunta());
        knowledgeBase.setResposta(requestDto.getResposta());
        knowledgeBase.setCategoria(requestDto.getCategoria());
        knowledgeBase.setUpdatedBy(user);

        KnowledgeBase updated = knowledgeBaseRepository.save(knowledgeBase);
        return convertToDto(updated);
    }

    public void delete(Long id) {
        if (!knowledgeBaseRepository.existsById(id)) {
            throw new EntityNotFoundException("KnowledgeBase item not found with id: " + id);
        }
        knowledgeBaseRepository.deleteById(id);
    }

    private KnowledgeBaseResponseDto convertToDto(KnowledgeBase knowledgeBase) {
        KnowledgeBaseResponseDto dto = new KnowledgeBaseResponseDto();
        dto.setId(knowledgeBase.getId());
        dto.setTitulo(knowledgeBase.getTitulo());
        dto.setPergunta(knowledgeBase.getPergunta());
        dto.setResposta(knowledgeBase.getResposta());
        dto.setCategoria(knowledgeBase.getCategoria());
        dto.setUpdatedAt(knowledgeBase.getUpdatedAt());
        if (knowledgeBase.getUpdatedBy() != null) {
            dto.setUpdatedByName(knowledgeBase.getUpdatedBy().getNome());
        }
        return dto;
    }
}
