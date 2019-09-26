package dev.idion.idionkim.board.rest.domain.projection;

import dev.idion.idionkim.board.rest.domain.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "getOnlyName", types = { User.class })
public interface UserOnlyContainName {

	String getName();
}
