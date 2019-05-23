package apiPosting;

import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.api.PagePostData;
import org.springframework.social.facebook.api.Facebook;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request;
import models.Posts.Data;
import models.Posts;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FacebookApiPosting {

    private final static String URL = "https://graph.facebook.com/";
    private OkHttpClient httpClient = new OkHttpClient();

    private String userAT = "EAAFvhvPUG3gBADAaWZABCZA3UgQagCpry6OqOay2VF2E6APcCqn0P5ep7BPa2ofjsATiVfIEGkHSDcx1hXJ8" +
            "1tTFkWRaNn1IZBnoNWm7ZCfGiPwtSVurCXeDuNJjQrZBfX8qYK3mBkZBNo3LxWmTC1bDmTmZAZAHMrKZCCiYgvVjFPQZDZD";

    private Facebook facebook = new FacebookTemplate(userAT);
    private String pageId = "651602118599464";
    private static String postMessage;

    public Map<String, String> createPost() {
        String id = "";
        postMessage = new Faker().chuckNorris().fact();
        id = facebook.pageOperations().post(new PagePostData(pageId).message(postMessage));
        Map<String, String> post = new HashMap<>();
        post.put(postMessage, id);
        return post;
    }

    public String getPosts() {
        String token = facebook.pageOperations().getAccessToken(pageId);
        Request request = new Request.Builder()
                .url(URL + pageId + "/feed?access_token=" + token)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occured!";
        }
    }

    public String updatePost(String id) {
        String token = facebook.pageOperations().getAccessToken(pageId);
        String updatedMessage = new Faker().beer().name() + " like to be drunk in the " + new Faker().gameOfThrones().city();
        Request request = new Request.Builder()
                .url(String.format("%s%s/?access_token=%s&message=%s", URL, id, token, updatedMessage))
                .post(RequestBody.create(null, new byte[]{}))
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return updatedMessage;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occured!";
        }
    }

    public String deletePost(String id) {
        String token = facebook.pageOperations().getAccessToken(pageId);
        Request request = new Request.Builder()
                .url(URL + id + "?access_token=" + token)
                .delete()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occured!";
        }
    }

    public boolean isPostExists(String allPosts, Map<String, String> post) {
        Gson gson = new Gson();
        Posts posts = gson.fromJson(allPosts, Posts.class);
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
