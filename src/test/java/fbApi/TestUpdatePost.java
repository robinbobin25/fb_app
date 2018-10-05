package fbApi;

import io.restassured.response.Response;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
public class TestUpdatePost extends BaseApiTest {

    private final String POST_UPDATE_MESSAGE = "This message is updated by Graph API";
    private Response updateResponse;

    @Before
    public void publishNewPostToPage() {
        Response publishResponse = getFbMethods().createPost(getSettings().getPageId(), postMessage, getSettings().getAccessToken());
        createdPostId = publishResponse.jsonPath().get("id").toString();
        updateResponse = getFbMethods().updatePost(createdPostId, POST_UPDATE_MESSAGE, getSettings().getAccessToken());
    }

    @Test
    public void testPostUpdatedWithSuccessStatus() {
        assertThat(updateResponse.jsonPath().get("success").toString()).isNotEmpty().isEqualTo("true");
    }

    @Test
    public void testPageFeedHasUpdatedPostWithSentData() {
        Response getFeedResponse = getFbMethods().getPageFeed(getSettings().getPageId(), getSettings().getAccessToken());
        assertThat(getFeedResponse).as("Feed response should have at least one post").hasNoNullFieldsOrProperties();
        assertThat(getFeedResponse.jsonPath().getList("data.id")).as("Feed response should contain the id of updated post").containsOnlyOnce(createdPostId);
        assertThat(getFeedResponse.jsonPath().getList("data.message")).as("Feed response should contain the updated message").containsOnlyOnce(POST_UPDATE_MESSAGE);
    }

    @After
    public void deleteCreatedPosts() {
        Response deleteResponse = getFbMethods().deletePost(createdPostId, getSettings().getAccessToken());
        assertThat(deleteResponse.jsonPath().get("success").toString()).as("The post should be deleted").isEqualTo("true");
    }

}