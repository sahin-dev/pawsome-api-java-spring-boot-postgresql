package com.pawsome.api.common;

import java.util.List;

import org.springframework.data.domain.Page;


public record PaginatedResponse<T>(
    List<T> data,
    Meta meta
) {
    public record Meta(
        int currentPage,
        int totalPages,
        long totalElements,
        int pageSize
    ) {}

    // Helper to convert a Spring Page to this DTO
    public static <T> PaginatedResponse<T> of(Page<T> page) {

        return new PaginatedResponse<>(
            page.getContent(),
            new Meta(
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize()
            )
        );
    }
}
