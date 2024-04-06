package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.entities.Step;
import br.com.rodrigues.todo.domain.entities.ToDoList;
import br.com.rodrigues.todo.domain.entities.User;
import br.com.rodrigues.todo.domain.repositories.StepRepository;
import br.com.rodrigues.todo.domain.repositories.ToDoListRepository;
import br.com.rodrigues.todo.domain.repositories.UserRepository;
import br.com.rodrigues.todo.domain.services.map.MapPage;
import br.com.rodrigues.todo.domain.services.map.StepsMapper;
import br.com.rodrigues.todo.domain.services.map.ToDoMapper;
import br.com.rodrigues.todo.domain.services.map.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class GeneralService {

    private final UserRepository userRepository;
    private final ToDoListRepository toDoListRepository;
    private final StepRepository stepRepository;

    private final UserMapper userMapper;
    private final ToDoMapper toDoMapper;
    private final StepsMapper stepsMapper;

    private final MapPage mapPage;

    public PageableDTO listAll(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        var allMapped = mapAll(usersPage.getContent());

        var response = userMapper.toListDto(allMapped);

        return mapPage.mapToResponseAll(response, usersPage);
    }

    private List<User> mapAll(List<User> users) {
        List<User> userResponse = new ArrayList<>();

        for (User user : users) {
            List<ToDoList> toDoLists = toDoListRepository.findAllToDoListByUserId(user.getId());

            for (ToDoList toDoList : toDoLists) {
                List<Step> stepsForToDo = stepRepository.findAllByToDoListId(toDoList.getId());
                toDoList.setSteps(stepsForToDo);
            }

            user.setList(toDoLists);
            userResponse.add(user);
        }
        return userResponse;
    }


    private List<Step> mapSteps(List<ToDoList> todoLists) {

        List<Step> stepResponse = new ArrayList<>();

        for (ToDoList todo : todoLists) {
            List<Step> stepsForToDo = stepRepository.findAllByToDoListId(todo.getId());
            stepResponse.addAll(stepsForToDo);
        }

        return stepResponse;
    }

}
