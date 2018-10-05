package fbApi;

import io.restassured.response.Response;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
public interface FbApiClient {

    Response createPost(String pageId, String postMessage, String accessToken);

    Response updatePost(String postId, String updateMessage, String accessToken);

    Response deletePost(String postId, String accessToken);

    Response getPageFeed(String pageId, String accessToken);
}
