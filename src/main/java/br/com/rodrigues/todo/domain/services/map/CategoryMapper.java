package br.com.rodrigues.todo.domain.services.map;

import br.com.rodrigues.todo.api.dto.category.CategoryRequestDTO;
import br.com.rodrigues.todo.api.dto.category.CategoryResponseDTO;
import br.com.rodrigues.todo.domain.entities.Category;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CategoryMapper {

    public Category toEntity(String userId, CategoryRequestDTO dto) {
        return new Category(
                dto.name(),
                userId
        );
    }

    public CategoryResponseDTO toDto(Category entity) {
        return new CategoryResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getUserId()
        );
    }

    public List<Category> toListEntity(String userId, List<CategoryRequestDTO> categories) {
        return categories.stream().map(dto -> toEntity(userId, dto)).toList();
    }

    public List<CategoryResponseDTO> toListDto(List<Category> categories) {
        return categories.stream().map(this::toDto).toList();
    }

    public Category updateEntity(Category entity, CategoryRequestDTO dto) {

        entity.setName(dto.name() == null ? entity.getName() : dto.name());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }
}
