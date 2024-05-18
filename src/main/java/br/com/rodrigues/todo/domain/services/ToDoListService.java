package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.todo.ToDoRequestDTO;
import br.com.rodrigues.todo.api.dto.todo.ToDoResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.entities.Step;
import br.com.rodrigues.todo.domain.entities.ToDoList;
import br.com.rodrigues.todo.domain.repositories.StepRepository;
import br.com.rodrigues.todo.domain.repositories.ToDoListRepository;
import br.com.rodrigues.todo.domain.services.map.MapPage;
import br.com.rodrigues.todo.domain.services.map.ToDoMapper;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.BusinessException;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor

@Service
public class ToDoListService {

    private final ToDoListRepository toDoListRepository;
    private final StepRepository stepRepository;

    private final UserService userService;
    private final CategoryService categoryService;

    private final MapPage mapPage;
    private final ToDoMapper todoMapper;

    public ToDoResponseDTO saveToDoBy(String userId, ToDoRequestDTO dto) {

        userService.validateUser(userId);

        categoryService.findByName(dto.category(), userId);

        var toDoList = todoMapper.toEntity(userId, dto);

        return todoMapper.toDto(toDoListRepository.save(toDoList));
    }

    public ToDoResponseDTO updateToDoBy(String userId, String todoId, ToDoRequestDTO dto) {

        userService.validateUser(userId);

        ToDoList toDoListForSave = todoMapper.update(validateToDoList(todoId, userId), dto);

        var response = updateToDoIsDone(toDoListForSave.getSteps(), toDoListForSave);

        toDoListRepository.save(response);

        return todoMapper.toDto(response);
    }

    public ToDoResponseDTO detailsToDoBy(String userId, String todoId) {
        userService.validateUser(userId);

        return todoMapper.toDto(validateToDoList(todoId, userId));
    }

    public PageableDTO findAllToDoBy(String userId, Pageable pageable, String status) {
        userService.validateUser(userId);
        Page<ToDoList> page = toDoListRepository.findAllToDoListByUserId(userId, pageable);

        List<ToDoResponseDTO> response;

        var listToDo = page.getContent().stream().filter(filtered -> filtered.getIsDone().equals(Boolean.parseBoolean(status))).toList();

        response = todoMapper.toListDto(listToDo);

        return mapPage.mapToResponseAll(response, page);
    }

    public PageableDTO findAllToDoFilterCustomBy(String userId, String priority, Pageable pageable) {
        userService.validateUser(userId);
        Page<ToDoList> page = toDoListRepository.findAllByUserIdAndPriority(userId, priority, pageable);
        List<ToDoList> filtered = page.getContent();
        var response = todoMapper.toListDto(filtered);
        return mapPage.mapToResponseAll(response, page);
    }

    public void deleteToDoBy(String userId, String todoId) {
        userService.validateUser(userId);

        validateToDoList(todoId,userId);

        List<Step> stepList = stepRepository.findAllByToDoListId(todoId);
        stepRepository.deleteAll(stepList);

        toDoListRepository.deleteById(todoId);
    }

//    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(fixedRate = 60000)
//    public void verifyDateLimitTodoList() {
//
//        List<ToDoList> listToDoList = toDoListRepository.findAll();
//
//        var todoListFilter = listToDoList.stream().filter(list -> list.getIsExpired().equals(false)).toList();
//
//        for (ToDoList list : todoListFilter) {
//            if (!LocalDate.now().isBefore(list.getLimitDate())) {
//                list.setIsExpired(true);
//                toDoListRepository.save(list);
//            }
//        }
//    }


    //Auxiliary methods
    public ToDoList validateToDoList(String todoId, String userId) {
        var user = userService.validateUser(userId);
        var todoList = toDoListRepository.findById(todoId).orElseThrow(
                () -> new NotFoundException("List does not exists in user"));

        if (!user.getId().equals(todoList.getUserId())){
            throw new BusinessException("This list does not correspond to that specific user");
        }

        return todoList;
    }

    private ToDoList updateToDoIsDone(List<Step> step, ToDoList todo) {

        boolean verify = true;

        for (Step steps : step) {
            if (!steps.getIsDone()) {
                verify = false;
                break;
            }
        }

        if (verify) {
            todo.setIsDone(true);
        }

        return todo;
    }
}
