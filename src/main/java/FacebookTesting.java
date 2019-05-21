import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class FacebookTesting {

    private final static String URL = "https://graph.facebook.com/";
    private final static String USER_ID = "105607550683592";
    private OkHttpClient httpClient = new OkHttpClient();
    private static String userAT = "EAAFvhvPUG3gBAB1nuZAcZAae62H0gCsw0W6FgkJOastTYfoxhNCxYB8fdxr6NWhV8PUkCpbIeNaQ" +
            "cjxFow6S3IgKgYC0E6D4e6MJOZAMK4z6lt1KZCEX0tNCzmNfrAXeBS20HE3VKBzAVqUNDfOLXx8qDjz38FmBNZBb2aJR" +
            "ksir3ELPH0fQWeXeXP1WpMcdmZBo2Vzd6UigZDZD";

    public static void main(String[] args) throws IOException {

        FacebookTesting facebookApi = new FacebookTesting();
        String response = facebookApi.readPosts(USER_ID + "/feed/?access_token=" + userAT);
        System.out.println(response);
    }

    private String readPosts(String endPoint) throws IOException {
        Request request = new Request.Builder()
                .url(URL + endPoint)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
