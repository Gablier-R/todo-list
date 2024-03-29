package br.com.rodrigues.todo.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Document
public class Todo {

    private String id;
    private String name;
    private Priorities priorities;
    private Boolean done = false;
    private List<Steps> steps = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    public Todo(String name, Priorities priorities) {
        this.name = name;
        this.priorities = (priorities == null) ? Priorities.MEDIUM : priorities;
    }
}
