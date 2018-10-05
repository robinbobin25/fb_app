package fbApi;

import io.restassured.response.Response;

import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
public class HttpFbApiClient implements FbApiClient {

    private String FB_API_URL = "https://graph.facebook.com/";

    private String FEED_PATH = "/feed";
    private String MESSAGE_PARAMETER = "?message=";

    @Override
    public Response createPost(String pageId, String postMessage, String accessToken) {
        Response createResponse = given().auth().oauth2(accessToken).post(FB_API_URL + pageId + FEED_PATH + MESSAGE_PARAMETER + postMessage);
        checkStatusOk(createResponse);
        Logger.getGlobal().info("Post was published");
        return createResponse;
    }

    @Override
    public Response updatePost(String postId, String updateMessage, String accessToken) {
        Response updateResponse = given().auth().oauth2(accessToken).post(FB_API_URL + postId + MESSAGE_PARAMETER + updateMessage);
        checkStatusOk(updateResponse);
        Logger.getGlobal().info("Post was updated");
        return updateResponse;
    }

    @Override
    public Response deletePost(String postId, String accessToken) {
        Response deleteResponse = given().auth().oauth2(accessToken).delete(FB_API_URL + postId);
        checkStatusOk(deleteResponse);
        Logger.getGlobal().info("Post was deleted");
        return deleteResponse;
    }

    @Override
    public Response getPageFeed(String pageId, String accessToken) {
        Response pageFeedResponse = given().auth().oauth2(accessToken).when().get(FB_API_URL + pageId + FEED_PATH);
        checkStatusOk(pageFeedResponse);
        return pageFeedResponse;
    }

    private void checkStatusOk(Response response) {
        assertThat(response.getStatusCode()).as("Status is not OK").isEqualTo(200);
    }

}
