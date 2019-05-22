import okhttp3.RequestBody;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.api.*;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Request;
import models.Post.Data;
import models.Post;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class FacebookTesting {

    private final static String URL = "https://graph.facebook.com/";
    //private final static String USER_ID = "105607550683592";
    //private final static String VAD_ID = "2154841421289457";
    private OkHttpClient httpClient = new OkHttpClient();

    private String vadAT = "EAAFvhvPUG3gBAI6kbk5xS4X8vPgkX5OUS6b" +
            "JTyWZAW4lyZAImHh2rCf8jT2tv6kyDhBEhRVA982lVJZAK8RQUu5h8MfEjYERW3Us" +
            "qLdBGWXZBAqZAmBffHJr62r8XQZBoZB0naZAskQZAQ41CkZClcP" +
            "UZAr35CExHAnzpaeWgyXWcfrAG87nQxJhgFeW7qqZAZBELlYtp22RBPbukYgZDZD";

    private Facebook facebook = new FacebookTemplate(vadAT);
    private String pageId = "651602118599464";
    private String pageAccessToken = "EAAFvhvPUG3gBAG5zMHCQaxCBZBU62MXZBUlzcxZCEW" +
            "XM3VspdhX5RDmTeEnbzFEyb8Ss21SM6c8H0s82bAjZCuMJ7JljAZB5ye3fK6oB2iIflCz88wKq" +
            "m54Skm5a7lYqJoPn59qaWHuO2ZCtU" +
            "HUctZBEsrTQwkoXdZCR6vWSZAEAssSEZBG1KzBZCHLRkqFK8WJN5mS8XZCWt88ywAZDZD";
    private static String postMessage;
    private String updatedMessage;
    private String id;


    public Map<String, String> createPost() {
        postMessage = new Faker().chuckNorris().fact();
        id = facebook.pageOperations().post(new PagePostData(pageId).message(postMessage));
        Map<String, String> post = new HashMap<>();
        post.put(postMessage, id);
        return post;
    }

    public static void main(String[] args) throws IOException {
        FacebookTesting testing = new FacebookTesting();
        Map<String, String> map = testing.createPost();
        System.out.println("Message is: " + map.keySet());
        System.out.println("Id is: " + map.values());
        String posts = testing.getPosts();
        System.out.println("Is post exists: " + testing.isPostExists(posts, map));
        testing.updatePost(map.get(postMessage));
    }


    private String getPosts() throws IOException {
        Request request = new Request.Builder()
                .url(URL + pageId + "/feed?access_token=" + pageAccessToken)
                .build();
         try (Response response = httpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private String updatePost(String id) throws IOException {
        updatedMessage = new Faker().beer().name() + " like to be drunk in the " + new Faker().gameOfThrones().city();
        String token = facebook.pageOperations().getAccessToken(pageId);
        Request request = new Request.Builder()
                .url(String.format("%s%s/?access_token=%s&message=%s", URL, id, token, updatedMessage))
                .post(RequestBody.create(null, new byte[]{}))
                .build();
        System.out.println("Request: " + request.toString());
        try (Response response = httpClient.newCall(request).execute()) {
            String r = response.body().toString();
            System.out.println("Response: " + r);
            return r;
        }
    }

    private String deletePost(Map<String, String> post) throws IOException {
        String token = facebook.pageOperations().getAccessToken(pageId);
        Request request = new Request.Builder()
                .url(URL + pageId + "/feed?access_token=" + token)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public boolean isPostExists(String allPosts, Map<String, String> post) {
        Gson gson = new Gson();
        Post posts = gson.fromJson(allPosts, Post.class);
        for (Map.Entry<String, String> pair : post.entrySet()) {
            for (Data data : posts.data) {
                if (data.message != null && data.message.equals(pair.getKey()) && data.id.equals(pair.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }
}
