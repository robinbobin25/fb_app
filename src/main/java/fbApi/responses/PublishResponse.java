package fbApi.responses;

import io.restassured.response.Response;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
public class PublishResponse {

    public static String getPostId(Response response) {
        return response.jsonPath().get("id").toString();
    }
}
