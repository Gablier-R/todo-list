package br.com.rodrigues.todo.domain.entities;


import br.com.rodrigues.todo.api.dto.auth.LoginRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "user")
public class User {

    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private String lastName;

    @Indexed(unique = true)
    private String email;
    private Date dateOfBirth;
    private List<ToDoList> list = new ArrayList<>();
    private List<Note> notes =  new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private String password;


    public User(String name, String lastName, String email, Date dateOfBirth, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
    }

    public Boolean isLoginCorrect(LoginRequestDTO requestDTO, PasswordEncoder encoder){
        return encoder.matches(requestDTO.password(), this.password);
    }
}
