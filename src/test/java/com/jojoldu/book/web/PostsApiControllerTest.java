package com.jojoldu.book.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.jojoldu.book.domain.posts.Posts;
import com.jojoldu.book.domain.posts.PostsRepository;
import com.jojoldu.book.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.web.dto.PostsUpdateRequestDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PostsRepository postsRepository;

	@After
	public void tearDown() throws Exception {
		postsRepository.deleteAll();
	}

	@Test
	public void savePosts() throws Exception {

		String title = "제목_테스트_01";
		String content = "내용_테스트_01";
		String author = "작성자_테스트_01";

		PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder()
		                                                             .title(title)
		                                                             .content(content)
		                                                             .author(author)
		                                                             .build();

		String url = "http://localhost:" + port + "/api/v1/posts";

		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, postsSaveRequestDto, Long.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		List<Posts> postsList = postsRepository.findAll();
		Posts posts = postsList.get(0);
		assertThat(posts.getTitle()).isEqualTo(title);
		assertThat(posts.getContent()).isEqualTo(content);
		assertThat(posts.getAuthor()).isEqualTo(author);
	}

	@Test
	public void updatePosts() throws Exception {

		String title = "제목_테스트";
		String content = "내용_테스트";
		String author = "작성자_테스트";

		Posts savePosts = postsRepository.save(Posts.builder()
		                                            .title(title)
		                                            .content(content)
		                                            .author(author)
		                                            .build());

		Long updateId = savePosts.getId();
		String expectedTitle = "제목_테스트_수정";
		String expectedContent = "내용_테스트_수정";

		PostsUpdateRequestDto postsUpdateRequest = PostsUpdateRequestDto.builder()
		                                                                .title(expectedTitle)
		                                                                .content(expectedContent)
		                                                                .build();

		String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

		HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(postsUpdateRequest);

		ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		List<Posts> postsList = postsRepository.findAll();
		Posts posts = postsList.get(0);
		assertThat(posts.getTitle()).isEqualTo(expectedTitle);
		assertThat(posts.getContent()).isEqualTo(expectedContent);
	}
}
