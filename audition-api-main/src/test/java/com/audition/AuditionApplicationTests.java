package com.audition;

import com.audition.common.AuditionConstants;
import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import com.audition.service.AuditionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuditionApplicationTests {

    @InjectMocks
    private AuditionService auditionService;

    @Mock
    private AuditionIntegrationClient auditionIntegrationClient;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void testGetPostsInService() {
        when(auditionIntegrationClient.getPosts()).thenReturn(Collections.singletonList(getAuditionPost()));

        List<AuditionPost> posts = auditionService.getPosts();

        assertNotNull(posts);
        assertNotNull(posts.get(0));
        assertEquals(AuditionConstants.TITLE, posts.get(0).getTitle());
        assertEquals(AuditionConstants.POST_BODY, posts.get(0).getBody());

        verify(auditionIntegrationClient, times(1)).getPosts();
    }

    @Test
    void testGetPostById() {
        when(auditionIntegrationClient.getPostById(AuditionConstants.POST_ID)).thenReturn(getAuditionPost());

        AuditionPost post = auditionService.getPostById(AuditionConstants.POST_ID);

        assertNotNull(post);
        assertEquals(AuditionConstants.TITLE, post.getTitle());
        assertEquals(AuditionConstants.POST_BODY, post.getBody());

        verify(auditionIntegrationClient, times(1)).getPostById(AuditionConstants.POST_ID);
    }

    @Test
    void testGetCommentsForPost() {
        when(auditionIntegrationClient.getcommentByPostId(AuditionConstants.POST_ID)).thenReturn(Collections.singletonList(getComment()));

        List<Comment> comments = auditionService.getCommentsForPost(AuditionConstants.POST_ID);

        assertNotNull(comments);
        assertNotNull(comments.get(0));
        assertEquals(AuditionConstants.NAME, comments.get(0).getName());
        assertEquals(AuditionConstants.EMAIL, comments.get(0).getEmail());
        assertEquals(AuditionConstants.COMMENT_BODY, comments.get(0).getBody());

        verify(auditionIntegrationClient, times(1)).getcommentByPostId(AuditionConstants.POST_ID);
    }

    @Test
    void testGetCommentsForPostId() {
        when(auditionIntegrationClient.getCommentsForPostId(AuditionConstants.POST_ID)).thenReturn(Collections.singletonList(getComment()));

        List<Comment> comments = auditionService.getCommentsForPostId(AuditionConstants.POST_ID);

        assertNotNull(comments);
        assertNotNull(comments.get(0));
        assertEquals(AuditionConstants.NAME, comments.get(0).getName());
        assertEquals(AuditionConstants.EMAIL, comments.get(0).getEmail());
        assertEquals(AuditionConstants.COMMENT_BODY, comments.get(0).getBody());

        verify(auditionIntegrationClient, times(1)).getCommentsForPostId(AuditionConstants.POST_ID);
    }

    private static AuditionPost getAuditionPost() {
        AuditionPost auditionPost = new AuditionPost();
        auditionPost.setId(1);
        auditionPost.setId(1);
        auditionPost.setTitle(AuditionConstants.TITLE);
        auditionPost.setBody(AuditionConstants.POST_BODY);
        return auditionPost;
    }

    private static Comment getComment() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setPostId(1);
        comment.setName(AuditionConstants.NAME);
        comment.setEmail(AuditionConstants.EMAIL);
        comment.setBody(AuditionConstants.COMMENT_BODY);
        return comment;
    }


}

