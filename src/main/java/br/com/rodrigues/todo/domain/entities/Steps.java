package br.com.rodrigues.todo.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Document
public class Steps {

    private String id;
    private String description;
    private Boolean done = false;
}
