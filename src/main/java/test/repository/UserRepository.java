package test.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import test.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByPhone(String phone);
}
