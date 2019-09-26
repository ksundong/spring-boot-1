package dev.idion.idionkim.board.rest.controller;

import dev.idion.idionkim.board.rest.domain.Board;
import dev.idion.idionkim.board.rest.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
public class BoardRestController {

	private BoardRepository boardRepository;

	public BoardRestController(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	@GetMapping("/boards")
	public @ResponseBody Resources<Board> simpleBoard(@PageableDefault Pageable pageable) {
		Page<Board> boardList = boardRepository.findAll(pageable);

		PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(), boardList.getNumber(), boardList.getTotalElements());

		PagedResources<Board> resources = new PagedResources<>(boardList.getContent(), pageMetadata);
		resources.add(linkTo(methodOn(BoardRestController.class).simpleBoard(pageable)).withSelfRel());
		return resources;
	}

	@PostMapping
	public ResponseEntity<?> postBoard(@RequestBody Board board) {
		board.setCreatedDateNow();
		boardRepository.save(board);
		return new ResponseEntity<>("{}", HttpStatus.CREATED);
	}

	@PutMapping("/{idx}")
	@Transactional
	public ResponseEntity<?> putBoard(@PathVariable("idx")Long idx, @RequestBody Board board) {
		Board persistBoard = boardRepository.getOne(idx);
		persistBoard.update(board);
		boardRepository.save(persistBoard);
		return new ResponseEntity<>("{}", HttpStatus.OK);
	}

	@DeleteMapping("/{idx}")
	@Transactional
	public ResponseEntity<?> deleteBoard(@PathVariable("idx")Long idx) {
		boardRepository.deleteById(idx);;
		return new ResponseEntity<>("{}", HttpStatus.OK);
	}
}
