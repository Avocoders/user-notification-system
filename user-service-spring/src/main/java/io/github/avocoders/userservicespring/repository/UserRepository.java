package io.github.avocoders.userservicespring.repository;

import io.github.avocoders.userservicespring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
