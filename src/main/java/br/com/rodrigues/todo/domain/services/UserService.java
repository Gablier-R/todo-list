package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.user.UserRequestDTO;
import br.com.rodrigues.todo.api.dto.user.UserResponseDTO;
import br.com.rodrigues.todo.domain.entities.User;
import br.com.rodrigues.todo.domain.repositories.UserRepository;
import br.com.rodrigues.todo.domain.services.map.UserMapper;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.BusinessException;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO saveUser(UserRequestDTO dto) {
        var entity = userMapper.toEntity(dto);

        var userExists = userRepository.existsByEmail(entity.getEmail());

        if (userExists) {
            throw new BusinessException("User already exists");
        }

        var response = userRepository.save(entity);

        return userMapper.toDto(response);
    }

    public UserResponseDTO updateUser(String userId, UserRequestDTO dto) {

        var entity = validateUser(userId);

        var userResponse = userMapper.updateUser(entity, dto);

        userRepository.save(userResponse);

        return userMapper.toDto(userResponse);
    }

    public UserResponseDTO listById(String id) {
        return userMapper.toDto(validateUser(id));
    }

    public void deleteById(String id) {
        var entity = validateUser(id);

        userRepository.deleteById(entity.getId());
    }

    public List<UserResponseDTO> listAllUsers() {
        var entity = userRepository.findAll();
        return userMapper.toListDto(entity);
    }

    public User validateUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User does not exists"));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
