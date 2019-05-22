import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.social.facebook.api.*;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

import java.io.IOException;

public class FacebookTesting {

    private final static String URL = "https://graph.facebook.com/";
    private final static String USER_ID = "105607550683592";
    private OkHttpClient httpClient = new OkHttpClient();

        //"https://graph.facebook.com/{your-page-post-id}
        //        ?message=Happy%20Holidays!
        //    &access_token={your-page-access-token}"

    private static String userAT = "EAAFvhvPUG3gBACGxZCszbZCEIRGGxVTPoex2E" +
            "aZAH3KSAHEMAKYZAsaYLLfEWHfEkUOfQnTjxHRiCEhRSw2njnqiTyZBZCeBn2laDZBUDmvL" +
            "ib7jOY5A8luiABELCzS5UZCB1CIO5VIGfZCva8jUx1qUToGSR" +
            "Om2lo19EBF4NWM4vROfnYst7KE0HWGbL7Kf9WH6vLoLcRyDQSAZDZD";

    public static void main(String[] args) throws IOException {

        Facebook facebook = new FacebookTemplate(userAT);
        facebook.feedOperations().post(new PostData(USER_ID).message("Hello"));
        PagedList<Post> posts = facebook.feedOperations().getPosts();
        for (Post p : posts) {
            System.out.println(p.getMessage());
        }

        FacebookTesting facebookApi = new FacebookTesting();

    }


    private String post(String endPoint) throws IOException {
        Request request = new Request.Builder()
                .url(URL + endPoint)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
