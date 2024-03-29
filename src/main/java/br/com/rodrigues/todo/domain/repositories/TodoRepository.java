package br.com.rodrigues.todo.domain.repositories;

import br.com.rodrigues.todo.domain.entities.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
}
