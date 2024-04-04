package br.com.rodrigues.todo.api.dto.utils;


import java.util.List;

public record PageableDTO<T>(
        List <T> content,
        int pageNo,
        int pageSize,
        int totalPages,
        long totalElementsInPage,
        boolean last
) {
}
