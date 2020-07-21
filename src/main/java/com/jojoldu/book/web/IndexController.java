package com.jojoldu.book.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jojoldu.book.config.auth.LoginUser;
import com.jojoldu.book.config.auth.dto.SessionUser;
import com.jojoldu.book.service.posts.PostsService;
import com.jojoldu.book.web.dto.PostsResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {

	private final PostsService postsService;

	@GetMapping("/")
	public String index(Model model, @LoginUser SessionUser user) {
		
		model.addAttribute("posts", postsService.findAllDesc());

		if (user != null) {
			model.addAttribute("userName", user.getName());
		}

		return "index";
	}

	@GetMapping("/posts/save")
	public String postsSave() {
		return "posts-save";
	}

	@GetMapping("/posts/update/{id}")
	public String postsUpdate(@PathVariable Long id, Model model) {

		PostsResponseDto postsResponseDto = postsService.findById(id);
		model.addAttribute("post", postsResponseDto);

		return "posts-update";
	}
}
