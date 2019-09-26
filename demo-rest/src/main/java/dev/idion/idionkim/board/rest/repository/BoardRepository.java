package dev.idion.idionkim.board.rest.repository;

import dev.idion.idionkim.board.rest.domain.Board;
import dev.idion.idionkim.board.rest.domain.projection.BoardOnlyContainTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(excerptProjection = BoardOnlyContainTitle.class)
public interface BoardRepository extends JpaRepository<Board, Long> {

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	<S extends Board> S save(S entity);
}
