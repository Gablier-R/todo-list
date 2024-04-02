package br.com.rodrigues.todo.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "todo")
public class ToDoList {

    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private Priorities priorities;
    private LocalDate limitDate;
    private Boolean isDone = false;
    private List<Step> steps = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    public ToDoList(String name, Priorities priorities, LocalDate limitDate) {
        this.name = name;
        this.priorities = (priorities == null) ? Priorities.MEDIUM : priorities;
        this.limitDate = limitDate;
    }
}
