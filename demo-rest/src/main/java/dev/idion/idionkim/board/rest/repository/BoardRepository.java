package dev.idion.idionkim.board.rest.repository;

import dev.idion.idionkim.board.rest.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
public interface BoardRepository extends JpaRepository<Board, Long> {

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	<S extends Board> S save(S entity);
}
