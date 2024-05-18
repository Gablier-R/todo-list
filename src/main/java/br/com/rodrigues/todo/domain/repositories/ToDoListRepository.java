package br.com.rodrigues.todo.domain.repositories;

import br.com.rodrigues.todo.domain.entities.ToDoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoListRepository extends MongoRepository<ToDoList, String> {

    Page<ToDoList> findAllToDoListByUserId (String userId, Pageable pageable);

    List<ToDoList> findAllToDoListByUserId (String userId);

    Page<ToDoList> findAllByUserIdAndPriority(String userId, String priority, Pageable pageable);

    Integer countByUserId(String userId);

}
