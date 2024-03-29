package br.com.rodrigues.todo.domain.services.map;

import br.com.rodrigues.todo.api.dto.todo.TodoResponseDTO;
import br.com.rodrigues.todo.api.dto.user.UserRequestDTO;
import br.com.rodrigues.todo.api.dto.user.UserResponseDTO;
import br.com.rodrigues.todo.domain.entities.Todo;
import br.com.rodrigues.todo.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor

@Component
public class UserMapper {

    private final TodoMapper todoMapper;

    public User toEntity (UserRequestDTO dto){
        return new User(
                dto.email(),
                dto.lastName(),
                dto.email(),
                dto.dateOfBirth()
        );
    }

    public UserResponseDTO toDto (User entity){
        return new UserResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getDateOfBirth(),
                todoMapper.toList(entity.getTodo()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
