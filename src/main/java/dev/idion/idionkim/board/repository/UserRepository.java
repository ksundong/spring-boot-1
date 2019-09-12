package dev.idion.idionkim.board.repository;

import dev.idion.idionkim.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
