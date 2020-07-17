package com.jojoldu.book.service.posts;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jojoldu.book.domain.posts.PostsRepository;
import com.jojoldu.book.web.dto.PostsSaveRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostsService {

	private final PostsRepository postsRepository;

	@Transactional
	public Long save(PostsSaveRequestDto postsSaveRequestDto) {		
		return postsRepository.save(postsSaveRequestDto.toEntity())
		                      .getId();
	}
}
