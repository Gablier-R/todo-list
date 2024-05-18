package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.user.UserRequestDTO;
import br.com.rodrigues.todo.api.dto.user.UserResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.entities.*;
import br.com.rodrigues.todo.domain.repositories.*;
import br.com.rodrigues.todo.domain.services.map.MapPage;
import br.com.rodrigues.todo.domain.services.map.UserMapper;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.BusinessException;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ToDoListRepository toDoListRepository;
    private final StepRepository stepRepository;
    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    private final UserMapper userMapper;


    public UserResponseDTO saveUser(UserRequestDTO dto) {
        var entity = userMapper.toEntity(dto);

        if (userRepository.existsByEmail(entity.getEmail())) {
            throw new BusinessException("User already exists");
        }
        return userMapper.toDto(userRepository.save(entity));
    }

    public UserResponseDTO updateUser(String userId, UserRequestDTO dto) {

        var userResponse = userMapper.updateUser(validateUser(userId), dto);

        userRepository.save(userResponse);

        return userMapper.toDto(userResponse);
    }

    public UserResponseDTO listById(String id) {
        return userMapper.toDto(validateUser(id));
    }


    public void deleteUser(String userId) {
        var entity = validateUser(userId);

        List<ToDoList> toDoLists = toDoListRepository.findAllToDoListByUserId(userId);

        for (ToDoList toDoList : toDoLists) {
            List<Step> stepList = stepRepository.findAllByToDoListId(toDoList.getId());
            stepRepository.deleteAll(stepList);
        }
        toDoListRepository.deleteAll(toDoLists);

        for (Note notes : noteRepository.findAllNoteByUserId(userId)) {
            noteRepository.delete(notes);
        }

        for (Category categories : categoryRepository.findAllCategoryByUserId(userId)) {
            categoryRepository.delete(categories);
        }

        userRepository.delete(entity);
    }

    public User validateUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User does not exists"));
    }


}
