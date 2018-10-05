package fbApi;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TestDeletePost extends BaseApiTest {

    private Response deleteResponse;

    @Before
    public void publishNewPostToPage() {
        Response publishResponse = getFbMethods().createPost(getSettings().getPageId(), postMessage, getSettings().getAccessToken());
        createdPostId = publishResponse.jsonPath().get("id").toString();
        deleteResponse = getFbMethods().deletePost(createdPostId, getSettings().getAccessToken());
    }

    @Test
    public void testDeleteResponseIsValid() {
        assertThat(deleteResponse.jsonPath().get("success").toString()).as("The post should be deleted with success").isNotEmpty().isEqualTo("true");
    }

    @Test
    public void testPageFeedDoesNotHavePost() {
        Response getFeedResponse = getFbMethods().getPageFeed(getSettings().getPageId(), getSettings().getAccessToken());
        assertThat(getFeedResponse.jsonPath().getList("data.id")).as("Feed response should not contain the id of updated post").doesNotContain(createdPostId);
        assertThat(getFeedResponse.jsonPath().getList("data.message")).as("Feed response should not contain the updated message").doesNotContain(postMessage);
    }

}
