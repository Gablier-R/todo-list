package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.user.UserRequestDTO;
import br.com.rodrigues.todo.api.dto.user.UserResponseDTO;
import br.com.rodrigues.todo.domain.repositories.UserRepository;
import br.com.rodrigues.todo.domain.services.map.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserResponseDTO saveUser (UserRequestDTO userRequestDTO){
        var entity = mapper.toEntity(userRequestDTO);

        var response = userRepository.save(entity);

        return mapper.toDto(response);
    }

}
