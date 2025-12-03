package com.semead.chatbot.java_log_service.controller;

import com.semead.chatbot.java_log_service.dto.KnowledgeBaseRequestDto;
import com.semead.chatbot.java_log_service.dto.KnowledgeBaseResponseDto;
import com.semead.chatbot.java_log_service.dto.PaginatedResponseDto;
import com.semead.chatbot.java_log_service.service.KnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/knowledgebase")
public class KnowledgeBaseController {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<KnowledgeBaseResponseDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(knowledgeBaseService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<KnowledgeBaseResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(knowledgeBaseService.findById(id));
    }

    @PostMapping
    public ResponseEntity<KnowledgeBaseResponseDto> create(@RequestBody KnowledgeBaseRequestDto requestDto) {
        KnowledgeBaseResponseDto created = knowledgeBaseService.create(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeBaseResponseDto> update(@PathVariable Long id, @RequestBody KnowledgeBaseRequestDto requestDto) {
        return ResponseEntity.ok(knowledgeBaseService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        knowledgeBaseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
