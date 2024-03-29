package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.steps.StepsRequestDTO;
import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.domain.entities.Steps;
import br.com.rodrigues.todo.domain.entities.Todo;
import br.com.rodrigues.todo.domain.repositories.StepsRepository;
import br.com.rodrigues.todo.domain.services.map.StepsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class StepsService {

    private final TodoService todoService;
    private final StepsRepository stepsRepository;
    private final StepsMapper stepsMapper;


    public List<StepsResponseDTO> saveStep (String id, List<StepsRequestDTO>  dto){

        var todo = todoService.findById(id);
        List<Steps> steps = todo.getSteps();

        var entity = stepsMapper.toListEntity(dto);
        List<Steps> response = new ArrayList<>();

        for(Steps step : entity){
            var savedStep =  stepsRepository.save(step);
            response.add(savedStep);
        }

        return stepsMapper.toListDto(response);
    }



    public List<StepsResponseDTO> save(String id, List<StepsRequestDTO> dtoList) {
        // Encontrar o Todo pelo ID
        Todo todo = todoService.findById(id);


        // Mapear os Steps do DTO para entidades Steps
        List<Steps> stepsToAdd = stepsMapper.toListEntity(dtoList);

        // Adicionar os Steps ao Todo
        List<Steps> savedSteps = new ArrayList<>();
        for (Steps step : stepsToAdd) {
            //todo.addStep(step);
            todo.getSteps().add(step);// Adiciona o novo Step ao Todo
            Steps savedStep = stepsRepository.save(step); // Salva o Step no banco de dados
            savedSteps.add(savedStep); // Adiciona o Step salvo à lista de Steps salvos
        }

        // Atualizar o Todo no banco de dados para refletir as alterações
        //todoService.updateTodo(todo);

        // Mapear a lista de Steps salvos para StepsResponseDTO e retorná-la
        return stepsMapper.toListDto(savedSteps);
    }

}
