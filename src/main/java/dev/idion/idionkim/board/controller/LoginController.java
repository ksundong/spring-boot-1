package dev.idion.idionkim.board.controller;

import dev.idion.idionkim.board.annotaion.SocialUser;
import dev.idion.idionkim.board.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping(value = "/loginSuccess")
	public String loginComplete(@SocialUser User user) {
		return "redirect:/board/list";
	}

}
