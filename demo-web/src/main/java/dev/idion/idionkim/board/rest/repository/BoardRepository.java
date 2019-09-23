package dev.idion.idionkim.board.rest.repository;

import dev.idion.idionkim.board.rest.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
