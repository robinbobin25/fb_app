package fbApi;

import fbApi.responses.FeedResponse;
import fbApi.responses.ModifyResponse;
import fbApi.responses.PublishResponse;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
public class HttpFbApiClient implements FbApiClient {

    private final String FB_API_URL = "https://graph.facebook.com/";
    private final String FEED_PATH = "/feed";
    private final String MESSAGE_PARAMETER = "?message=";

    private final Logger LOGGER = LoggerFactory.getLogger(HttpFbApiClient.class);

    @Override
    public PublishResponse createPost(String pageId, String postMessage, String accessToken) {
        Response createResponse = given().auth().oauth2(accessToken).post(FB_API_URL + pageId + FEED_PATH + MESSAGE_PARAMETER + postMessage);
        LOGGER.info("Post was published");
        return new PublishResponse(createResponse);
    }

    @Override
    public ModifyResponse updatePost(String postId, String updateMessage, String accessToken) {
        Response updateResponse = given().auth().oauth2(accessToken).post(FB_API_URL + postId + MESSAGE_PARAMETER + updateMessage);
        LOGGER.info("Post was updated");
        return new ModifyResponse(updateResponse);
    }

    @Override
    public ModifyResponse deletePost(String postId, String accessToken) {
        Response deleteResponse = given().auth().oauth2(accessToken).delete(FB_API_URL + postId);
        LOGGER.info("Post was deleted");
        return new ModifyResponse(deleteResponse);
    }

    @Override
    public FeedResponse getPageFeed(String pageId, String accessToken) {
        Response pageFeedResponse = given().auth().oauth2(accessToken).when().get(FB_API_URL + pageId + FEED_PATH);
        return new FeedResponse(pageFeedResponse);
    }

}
