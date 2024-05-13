package br.com.rodrigues.todo.domain.repositories;

import br.com.rodrigues.todo.domain.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Boolean existsByEmail(String email);

    User findByEmail(String email);

}
