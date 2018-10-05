package fbApi;

import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
public class TestUpdatePost extends BaseApiTest {

    private static String POST_UPDATE_MESSAGE = "This message is updated by Graph API";
    private static Response updateResponse;

    @BeforeClass
    public static void publishNewPostToPage() {
        Response publishResponse = fbMethods.createPost(pageId, postMessage, pageAccessToken);
        createdPostId = publishResponse.jsonPath().get("id").toString();
        updateResponse = fbMethods.updatePost(createdPostId, POST_UPDATE_MESSAGE, pageAccessToken);
    }

    @Test
    public void testPostUpdatedWithSuccessStatus() {
        assertThat(updateResponse.jsonPath().get("success").toString()).isNotEmpty().isEqualTo("true");
    }

    @Test
    public void testPageFeedHasUpdatedPostWithSentData() {
        Response getFeedResponse = fbMethods.getPageFeed(pageId, pageAccessToken);
        assertThat(getFeedResponse).as("Feed response should have at least one post").hasNoNullFieldsOrProperties();
        assertThat(getFeedResponse.jsonPath().getList("data.id")).as("Feed response should contain the id of updated post").containsOnlyOnce(createdPostId);
        assertThat(getFeedResponse.jsonPath().getList("data.message")).as("Feed response should contain the updated message").containsOnlyOnce(POST_UPDATE_MESSAGE);
    }

    @AfterClass
    public static void deleteCreatedPosts() {
        fbMethods.deletePost(createdPostId, pageAccessToken);
    }

}

