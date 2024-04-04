package br.com.rodrigues.todo.domain.services.map;

import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapPage {

    public <T, U> PageableDTO<T> mapToResponseAll(List<T> content, Page<U> paginatedEntity) {
        return new PageableDTO<>(
                content,
                paginatedEntity.getNumber(),
                paginatedEntity.getSize(),
                paginatedEntity.getTotalPages(),
                paginatedEntity.getNumberOfElements(),
                paginatedEntity.isLast()
        );
    }
}
