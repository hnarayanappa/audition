package com.audition.integration;

import com.audition.common.exception.SystemException;
import com.audition.common.logging.AuditionLogger;
import com.audition.model.AuditionPost;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.audition.model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuditionIntegrationClient {

    private static final Logger log = LoggerFactory.getLogger(AuditionIntegrationClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuditionLogger auditionLogger;

    public List<AuditionPost> getPosts() {
        auditionLogger.info(log, "getPosts() is invoked");
        // TODO make RestTemplate call to get Posts from https://jsonplaceholder.typicode.com/posts
        String apiUrl = "https://jsonplaceholder.typicode.com/posts";
        AuditionPost[] posts = restTemplate.getForObject(apiUrl, AuditionPost[].class);

        if (posts != null) {
            return Arrays.asList(posts);
        } else {
            auditionLogger.info(log, "No Records exist");
            return Collections.emptyList();
        }
    }

    public AuditionPost getPostById(final String id) {
        auditionLogger.info(log, "getPostById() is invoked");
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {
            return restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/" + id, AuditionPost.class);
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException("Cannot find a Post with id " + id, "Resource Not Found", 404);
            } else {
                auditionLogger.error(log, e.getMessage());
                throw new SystemException("Unknown Error message" + e.getMessage(), e);
            }
        }
    }

    // TODO Write a method GET comments for a post from https://jsonplaceholder.typicode.com/posts/{postId}/comments - the comments must be returned as part of the post.
    public List<Comment> getcommentByPostId(final String postId) {
        auditionLogger.info(log, "getcommentByPostId() is invoked");
        String apiUrl = "https://jsonplaceholder.typicode.com/posts/" + postId + "/comments";
        Comment[] comments = restTemplate.getForObject(apiUrl, Comment[].class);
        if (comments != null) {
            return Arrays.asList(comments);
        } else {
            auditionLogger.info(log, "No Records exist");
            return Collections.emptyList();
        }
    }

    // TODO write a method. GET comments for a particular Post from https://jsonplaceholder.typicode.com/comments?postId={postId}.
    // The comments are a separate list that needs to be returned to the API consumers. Hint: this is not part of the AuditionPost pojo.
    public List<Comment> getCommentsForPostId(final String postId) {
        auditionLogger.info(log, "getCommentsForPostId() is invoked");
        String apiUrl = "https://jsonplaceholder.typicode.com/comments?postId=" + postId;
        Comment[] comments = restTemplate.getForObject(apiUrl, Comment[].class);
        if (comments != null) {
            return Arrays.asList(comments);
        } else {
            auditionLogger.info(log, "No Records exist");
            return Collections.emptyList();
        }
    }
}
