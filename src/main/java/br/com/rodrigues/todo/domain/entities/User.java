package br.com.rodrigues.todo.domain.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "list")
public class User {

    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private List<ToDoList> list = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    public User(String name, String lastName, String email, String dateOfBirth) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }
}
