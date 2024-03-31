package br.com.rodrigues.todo.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Step {

    @Id
    private String id = UUID.randomUUID().toString();
    private String description;
    private Boolean isDone = false;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    public Step(String description) {
        this.description = description;
    }
}
