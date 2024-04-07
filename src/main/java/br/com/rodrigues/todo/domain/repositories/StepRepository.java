package br.com.rodrigues.todo.domain.repositories;

import br.com.rodrigues.todo.domain.entities.Step;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends MongoRepository<Step, String> {

    Page<Step> findAllByToDoListId (String toDoListId, Pageable pageable);
    List<Step> findAllByToDoListId (String toDoListId);
}
