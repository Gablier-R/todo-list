package br.com.rodrigues.todo.domain.repositories;

import br.com.rodrigues.todo.domain.entities.Steps;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepsRepository extends MongoRepository<Steps, String> {
}
