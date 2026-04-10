package com.example.board.post;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void searchByTitleOrContent_withPaging() {
        Post a = new Post();
        a.setTitle("Hello Spring");
        a.setContent("content A");
        postRepository.save(a);

        Post b = new Post();
        b.setTitle("Other");
        b.setContent("Spring Data JPA");
        postRepository.save(b);

        Page<Post> page = postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                "spring",
                "spring",
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt")));

        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(page.getContent()).hasSize(2);
    }
}

