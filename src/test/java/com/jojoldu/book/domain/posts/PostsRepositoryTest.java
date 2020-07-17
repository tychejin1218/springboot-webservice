package com.jojoldu.book.domain.posts;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

	@Autowired
	PostsRepository postsRepository;

	@After
	public void cleanup() {
		postsRepository.deleteAll();
	}

	@Test
	public void saveAndFind() {

		String title = "제목_테스트";
		String content = "내용_테스트";
		String author = "작성자_테스트";

		postsRepository.save(Posts.builder()
		                          .title(title)
		                          .content(content)
		                          .author(author)
		                          .build());

		List<Posts> postsList = postsRepository.findAll();

		Posts posts = postsList.get(0);
		assertThat(posts.getTitle()).isEqualTo(title);
		assertThat(posts.getContent()).isEqualTo(content);
		assertThat(posts.getAuthor()).isEqualTo(author);
	}

	@Test
	public void baseTimeEntity() {

		String title = "제목_테스트";
		String content = "내용_테스트";
		String author = "작성자_테스트";

		LocalDateTime now = LocalDateTime.of(2020, 7, 17, 0, 0, 0);

		postsRepository.save(Posts.builder()
		                          .title(title)
		                          .content(content)
		                          .author(author)
		                          .build());

		List<Posts> postsList = postsRepository.findAll();

		Posts posts = postsList.get(0);
		
		System.out.println("==== ==== ==== ==== ==== ==== ==== ====");
		System.out.println("createdDate : " + posts.getCreatedDate());
		System.out.println("modifiedDate : " + posts.getModifiedDate());
		System.out.println("==== ==== ==== ==== ==== ==== ==== ====");
		
		assertThat(posts.getCreatedDate()).isAfter(now);
		assertThat(posts.getModifiedDate()).isAfter(now);
	}
}