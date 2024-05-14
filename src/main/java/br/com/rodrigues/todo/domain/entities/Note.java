package br.com.rodrigues.todo.domain.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "note")
public class Note {

    @Id
    private String id = UUID.randomUUID().toString();
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private String userId;

    public Note(String content, String userId) {
        this.content = content;
        this.userId = userId;
    }
}
