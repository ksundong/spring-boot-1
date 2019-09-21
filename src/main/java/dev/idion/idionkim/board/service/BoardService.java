package dev.idion.idionkim.board.service;

import dev.idion.idionkim.board.domain.Board;
import dev.idion.idionkim.board.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

	private final BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public Page<Board> findBoardList(Pageable pageable) {
//		1.5
//		pageable = new PageRequest(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
//		2.0
		pageable = PageRequest.of
				(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
		return boardRepository.findAll(pageable);
	}

	public Board findBoardByIdx(Long idx) {
//		1.5
//		return boardRepository.findOne(idx);
//		2.0
		return boardRepository.findById(idx).orElse(new Board());
	}

}
