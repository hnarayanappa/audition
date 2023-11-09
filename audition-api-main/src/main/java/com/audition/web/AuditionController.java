package com.audition.web;

import com.audition.common.logging.AuditionLogger;
import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import com.audition.service.AuditionService;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuditionController {

    private static final Logger log = LoggerFactory.getLogger(AuditionController.class);

    @Autowired
    AuditionService auditionService;

    @Autowired
    private AuditionLogger auditionLogger;


    // TODO Add a query param that allows data filtering. The intent of the filter is at developers discretion.
    @RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<AuditionPost>> getPosts(@RequestParam(value = "filter") final String filter) {

        auditionLogger.info(log, "getPosts() is invoked");
        // TODO Add logic that filters response data based on the query param
        try {
            List<AuditionPost> posts = auditionService.getPosts();
            if (filter != null && !filter.isEmpty()) {
                posts = filterPosts(posts, filter);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            auditionLogger.error(log, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<AuditionPost> getPostsById(@PathVariable("id") final String postId) {
        auditionLogger.info(log, "getPostsById() is invoked");
        // TODO Add input validation
        try {
            if (isValidPostId(postId)) {
                final AuditionPost auditionPost = auditionService.getPostById(postId);
                if (auditionPost != null) {
                    return new ResponseEntity<>(auditionPost, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            auditionLogger.error(log, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // TODO Add additional methods to return comments for each post. Hint: Check https://jsonplaceholder.typicode.com/
    @RequestMapping(value = "/posts/{postId}/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Comment> getCommentsForPost(@PathVariable("postId") final String postId) {
        auditionLogger.info(log, "getCommentsForPost() is invoked");
        return auditionService.getCommentsForPost(postId);
    }

    @RequestMapping(value = "/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Comment> getCommentsForPostId(@RequestParam(value = "postId") final String postId) {
        auditionLogger.info(log, "getCommentsForPostId() is invoked");
        return auditionService.getCommentsForPostId(postId);
    }

    private List<AuditionPost> filterPosts(List<AuditionPost> posts, String filter) {
        return posts.stream()
                .filter(post -> post.getTitle().contains(filter))
                .collect(Collectors.toList());
    }

    private boolean isValidPostId(String postId) {
        try {
            int id = Integer.parseInt(postId);
            return id > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
