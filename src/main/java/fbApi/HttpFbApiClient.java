package fbApi;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
public class HttpFbApiClient implements FbApiClient {

    private String FB_API_URL = "https://graph.facebook.com/";
    private String FEED_PATH = "/feed";
    private String MESSAGE_PARAMETER = "?message=";

    private final Logger LOGGER = LoggerFactory.getLogger(HttpFbApiClient.class);

    @Override
    public Response createPost(String pageId, String postMessage, String accessToken) {
        Response createResponse = given().auth().oauth2(accessToken).post(FB_API_URL + pageId + FEED_PATH + MESSAGE_PARAMETER + postMessage);
        checkStatusOk(createResponse);
        LOGGER.info("Post was published");
        return createResponse;
    }

    @Override
    public Response updatePost(String postId, String updateMessage, String accessToken) {
        Response updateResponse = given().auth().oauth2(accessToken).post(FB_API_URL + postId + MESSAGE_PARAMETER + updateMessage);
        checkStatusOk(updateResponse);
        LOGGER.info("Post was updated");
        return updateResponse;
    }

    @Override
    public Response deletePost(String postId, String accessToken) {
        Response deleteResponse = given().auth().oauth2(accessToken).delete(FB_API_URL + postId);
        checkStatusOk(deleteResponse);
        LOGGER.info("Post was deleted");
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
