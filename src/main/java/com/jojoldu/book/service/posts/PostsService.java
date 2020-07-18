package com.jojoldu.book.service.posts;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jojoldu.book.domain.posts.Posts;
import com.jojoldu.book.domain.posts.PostsRepository;
import com.jojoldu.book.web.dto.PostsListResponseDto;
import com.jojoldu.book.web.dto.PostsResponseDto;
import com.jojoldu.book.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.web.dto.PostsUpdateRequestDto;

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

	@Transactional
	public Long update(Long id, PostsUpdateRequestDto postsUpdateRequestDto) {

		Posts posts = postsRepository.findById(id)
		                             .orElseThrow(() -> new IllegalAccessError("해당 게시글이 없습니다. id=" + id));

		posts.update(postsUpdateRequestDto.getTitle(), postsUpdateRequestDto.getContent());

		return id;
	}

	public PostsResponseDto findById(Long id) {

		Posts entity = postsRepository.findById(id)
		                              .orElseThrow(() -> new IllegalAccessError("해당 게시글이 없습니다. id=" + id));

		return new PostsResponseDto(entity);
	}

	@Transactional(readOnly = true)
	public List<PostsListResponseDto> findAllDesc() {

		return postsRepository.findAllDesc()
		                      .stream()
		                      .map(PostsListResponseDto::new)
		                      .collect(Collectors.toList());
	}
	
	@Transactional
	public void delete(Long id) {

		Posts posts = postsRepository.findById(id)
		                             .orElseThrow(() -> new IllegalAccessError("해당 게시글이 없습니다. id=" + id));

		postsRepository.delete(posts);
	}
}
