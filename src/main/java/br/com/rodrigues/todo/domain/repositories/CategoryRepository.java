package br.com.rodrigues.todo.domain.repositories;

import br.com.rodrigues.todo.domain.entities.Category;
import br.com.rodrigues.todo.domain.entities.Note;
import br.com.rodrigues.todo.domain.entities.ToDoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Page<Category> findAllCategoryByUserId (String userId, Pageable pageable);
    List<Category> findAllCategoryByUserId (String userId);
}
