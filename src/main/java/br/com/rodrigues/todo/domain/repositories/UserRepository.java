package br.com.rodrigues.todo.domain.repositories;

import br.com.rodrigues.todo.domain.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Boolean existsByEmail(String email);
}
