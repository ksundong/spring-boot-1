package dev.idion.idionkim.board.rest.controller;

import dev.idion.idionkim.board.rest.domain.User;
import dev.idion.idionkim.board.rest.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class UserRestController {

	private UserRepository userRepository;

	public UserRestController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/users")
	public @ResponseBody Resources<User> users(@PageableDefault Pageable pageable) {
		Page<User> userList = userRepository.findAll(pageable);

		PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(), userList.getNumber(), userList.getTotalElements());

		PagedResources<User> resources = new PagedResources<>(userList.getContent(), pageMetadata);
		return resources;
	}
}
