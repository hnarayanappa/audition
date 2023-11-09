package com.audition.service;

import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionPost;
import java.util.List;

import com.audition.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditionService {

    @Autowired
    private AuditionIntegrationClient auditionIntegrationClient;

    public List<AuditionPost> getPosts() {
        return auditionIntegrationClient.getPosts();
    }

    public AuditionPost getPostById(final String postId) {
        return auditionIntegrationClient.getPostById(postId);
    }

    public List<Comment> getCommentsForPost(final String postId) {
        return auditionIntegrationClient.getcommentByPostId(postId);
    }

    public List<Comment> getCommentsForPostId(final String postId) {
        return auditionIntegrationClient.getCommentsForPostId(postId);
    }
}
