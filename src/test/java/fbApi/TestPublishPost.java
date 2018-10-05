package fbApi;

import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
public class TestPublishPost extends BaseApiTest {

    @BeforeClass
    public static void publishNewPostToPage() {
        Response publishResponse = fbMethods.createPost(pageId, postMessage, pageAccessToken);
        createdPostId = publishResponse.jsonPath().get("id").toString();
    }

    @Test
    public void testPostIsPublishedWithNotEmptyId() {
        assertThat(createdPostId).as("Publish response should have created post id").isNotEmpty();
    }

    @Test
    public void testPageFeedHasPublishedPostWithSentData() {
        Response getFeedResponse = fbMethods.getPageFeed(pageId, pageAccessToken);
        assertThat(getFeedResponse).as("Feed response should have at least one post").hasNoNullFieldsOrProperties();
        assertThat(getFeedResponse.jsonPath().getList("data.id")).as("Feed response should contain the same published post id").containsOnlyOnce(createdPostId);
        assertThat(getFeedResponse.jsonPath().getList("data.message")).as("Feed response should contain the same published post message").containsOnlyOnce(postMessage);
    }

    @AfterClass
    public static void deleteCreatedPosts() {
        fbMethods.deletePost(createdPostId, pageAccessToken);
    }

}
