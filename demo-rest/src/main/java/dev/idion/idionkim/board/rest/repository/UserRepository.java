package dev.idion.idionkim.board.rest.repository;

import dev.idion.idionkim.board.rest.domain.User;
import dev.idion.idionkim.board.rest.domain.projection.UserOnlyContainName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = UserOnlyContainName.class)
public interface UserRepository extends JpaRepository<User, Long> {
}
