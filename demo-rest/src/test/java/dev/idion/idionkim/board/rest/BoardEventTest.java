package dev.idion.idionkim.board.rest;

import dev.idion.idionkim.board.rest.domain.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
public class BoardEventTest {
	private TestRestTemplate testRestTemplate = new TestRestTemplate("adminUser", "test");

	@Test
	public void saveGenerateCreatedDate() {
		Board createdBoard = createBoard();
		assertNotNull(createdBoard.getCreatedDate());
	}

	@Test
	public void modifyGenerateUpdatedDate() {
		Board createdBoard = createBoard();
		Board updatedBoard = updateBoard(createdBoard);
		assertNotNull(updatedBoard.getUpdatedDate());
	}

	private Board createBoard() {
		Board board = Board.builder().title("저장 이벤트 테스트").build();
		return testRestTemplate.postForObject("http://127.0.0.1:8081/api/boards", board, Board.class);
	}

	private Board updateBoard(Board createdBoard) {
		String updateUri = "http://127.0.0.1:8081/api/boards/1";
		testRestTemplate.put(updateUri, createdBoard);
		return testRestTemplate.getForObject(updateUri, Board.class);
	}
}
