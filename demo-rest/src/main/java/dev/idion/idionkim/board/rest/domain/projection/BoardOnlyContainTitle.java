package dev.idion.idionkim.board.rest.domain.projection;

import dev.idion.idionkim.board.rest.domain.Board;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "getOnlyTitle", types = { Board.class })
public interface BoardOnlyContainTitle {
	String getTitle();
}
