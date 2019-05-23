package api;

import apiPosting.FacebookApiPosting;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostTesting {

    FacebookApiPosting facebookApiPosting = new FacebookApiPosting();
    static Map<String, String> post;

    @Test()
    @Order(1)
    public void testCreate(){
    post = facebookApiPosting.createPost();
    boolean isPostExists = facebookApiPosting.isPostExists(facebookApiPosting.getPosts(),post);
    assertTrue(isPostExists, "There is no new post on the page!");
    }

    @Test
    @Order(2)
    public void testUpdate(){
        String id = "";
        for (Map.Entry<String,String> postData : post.entrySet()) {
            id = postData.getValue();
        }
        Map<String,String>  updatedPost = new HashMap<>();
        updatedPost.put(facebookApiPosting.updatePost(id), id);
        post = updatedPost;
        boolean isPostExists = facebookApiPosting.isPostExists(facebookApiPosting.getPosts(), updatedPost);
        assertTrue(isPostExists, "There is no new post on the page!");
    }
    @Test
    @Order(3)
    public void testDelete(){
        String id = "";
        for (Map.Entry<String,String> postData : post.entrySet()) {
            id = postData.getValue();
        }
        facebookApiPosting.deletePost(id);
        boolean isPostExists = facebookApiPosting.isPostExists(facebookApiPosting.getPosts(), post);
        assertFalse(isPostExists, "Post isn't deleted on the page!");
    }
}
