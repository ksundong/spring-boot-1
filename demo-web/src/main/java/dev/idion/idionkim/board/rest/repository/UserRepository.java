package dev.idion.idionkim.board.rest.repository;

import dev.idion.idionkim.board.rest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
