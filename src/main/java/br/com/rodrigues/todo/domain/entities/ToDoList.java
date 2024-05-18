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

@Document(collection = "toDoList")
public class ToDoList {

    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private String category;
    private Priority priority;
    private Boolean isDone = false;
    private LocalDate limitDate;
    private Boolean isExpired = false;
    private List<Step> steps = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private String userId;

    public ToDoList(String name, Priority priority, LocalDate limitDate, String category, String userId) {
        this.name = name;
        this.priority = (priority == null) ? Priority.MEDIUM : priority;
        this.limitDate = limitDate;
        this.category = category;
        this.userId = userId;
    }
}
