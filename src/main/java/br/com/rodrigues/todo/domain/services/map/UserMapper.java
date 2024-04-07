package br.com.rodrigues.todo.domain.services.map;

import br.com.rodrigues.todo.api.dto.user.UserRequestDTO;
import br.com.rodrigues.todo.api.dto.user.UserResponseDTO;
import br.com.rodrigues.todo.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor

@Component
public class UserMapper {

    private final ToDoMapper toDoMapper;

    public User toEntity(UserRequestDTO dto) {
        return new User(
                dto.name(),
                dto.lastName(),
                dto.email(),
                dto.dateOfBirth()
        );
    }

    public UserResponseDTO toDto(User entity) {
        return new UserResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getDateOfBirth(),
                toDoMapper.toListDto(entity.getList()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public List<UserResponseDTO> toListDto(List<User> users) {
        return users.stream().map(this::toDto).toList();
    }

    public User updateUser(User entity, UserRequestDTO dto) {

        entity.setName(dto.name() == null ? entity.getName() : dto.name());
        entity.setLastName(dto.lastName() == null ? entity.getLastName() : dto.lastName());
        entity.setEmail(dto.email() == null ? entity.getEmail() : dto.email());
        entity.setDateOfBirth(dto.dateOfBirth() == null ? entity.getDateOfBirth() : dto.dateOfBirth());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }
}
