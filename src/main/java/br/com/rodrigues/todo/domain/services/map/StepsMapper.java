package br.com.rodrigues.todo.domain.services.map;

import br.com.rodrigues.todo.api.dto.steps.StepsRequestDTO;
import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.domain.entities.Steps;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StepsMapper {

    public Steps toEntity (StepsRequestDTO dto){
        return new Steps(
                dto.description()
        );
    }

    public StepsResponseDTO toDto (Steps entity){
        return new StepsResponseDTO(
                entity.getId(),
                entity.getDescription(),
                entity.getDone(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public List<Steps> toListEntity (List<StepsRequestDTO> steps){
        return steps.stream().map(this::toEntity).toList();
    }

    public List<StepsResponseDTO> toListDto (List<Steps>  steps){
        return steps.stream().map(this::toDto).toList();
    }

}
