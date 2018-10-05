package fbApi;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
public class TestPublishPost extends BaseApiTest {

    @Before
    public void publishNewPostToPage() {
        Response publishResponse = getFbMethods().createPost(getSettings().getPageId(), postMessage, getSettings().getAccessToken());
        createdPostId = publishResponse.jsonPath().get("id").toString();
    }

    @Test
    public void testPostIsPublishedWithNotEmptyId() {
        assertThat(createdPostId).as("Publish response should have created post id").isNotEmpty();
    }

    @Test
    public void testPageFeedHasPublishedPostWithSentData() {
        Response getFeedResponse = getFbMethods().getPageFeed(getSettings().getPageId(), getSettings().getAccessToken());
        assertThat(getFeedResponse).as("Feed response should have at least one post").hasNoNullFieldsOrProperties();
        assertThat(getFeedResponse.jsonPath().getList("data.id")).as("Feed response should contain the same published post id").containsOnlyOnce(createdPostId);
        assertThat(getFeedResponse.jsonPath().getList("data.message")).as("Feed response should contain the same published post message").containsOnlyOnce(postMessage);
    }

    @After
    public void deleteCreatedPosts() {
        Response deleteResponse = getFbMethods().deletePost(createdPostId, getSettings().getAccessToken());
        assertThat(deleteResponse.jsonPath().get("success").toString()).as("The post should be deleted").isEqualTo("true");
    }

}
