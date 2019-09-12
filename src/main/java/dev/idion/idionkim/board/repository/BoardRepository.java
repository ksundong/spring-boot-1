package dev.idion.idionkim.board.repository;

import dev.idion.idionkim.board.domain.Board;
import dev.idion.idionkim.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

	Board findByUser(User user);

}
