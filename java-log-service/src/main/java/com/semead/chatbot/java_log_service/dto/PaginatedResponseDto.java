package com.semead.chatbot.java_log_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class PaginatedResponseDto<T> {
    private List<T> content;
    private int number;
    private int size;
    private long totalElements;
    private int totalPages;
}
