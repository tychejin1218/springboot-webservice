package com.jojoldu.book.web;

import javax.persistence.PostRemove;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jojoldu.book.service.posts.PostsService;
import com.jojoldu.book.web.dto.PostsResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {

	private final PostsService postsService;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("posts", postsService.findAllDesc());
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
