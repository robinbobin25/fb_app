package fbApi;

import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TestDeletePost extends BaseApiTest {

    private static Response deleteResponse;

    @BeforeClass
    public static void publishNewPostToPage() {
        Response publishResponse = fbMethods.createPost(pageId, postMessage, pageAccessToken);
        createdPostId = publishResponse.jsonPath().get("id").toString();
        deleteResponse = fbMethods.deletePost(createdPostId, pageAccessToken);
    }

    @Test
    public void testDeleteResponseIsValid() {
        assertThat(deleteResponse.jsonPath().get("success").toString()).isNotEmpty().isEqualTo("true");
    }

    @Test
    public void testPageFeedDoesNotHavePost() {
        Response getFeedResponse = fbMethods.getPageFeed(pageId, pageAccessToken);
        assertThat(getFeedResponse.jsonPath().getList("data.id")).as("Feed response should not contain the id of updated post").doesNotContain(createdPostId);
        assertThat(getFeedResponse.jsonPath().getList("data.message")).as("Feed response should not contain the updated message").doesNotContain(postMessage);
    }

}
