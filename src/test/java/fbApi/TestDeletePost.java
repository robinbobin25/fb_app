package fbApi;

import fbApi.data.PostData;
import fbApi.responses.FeedResponse;
import fbApi.responses.ModifyResponse;
import fbApi.responses.PublishResponse;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TestDeletePost extends BasePostTest {

    private String createdPostId;

    @Before
    public void publishNewPostToPage() {
        PublishResponse publishResponse = getFbMethods().createPost(getSettings().getPageId(), postMessage, getSettings().getAccessToken());
        assertThat(publishResponse.getStatusCode()).as("Status is not OK").isEqualTo(200);
        createdPostId = publishResponse.getPostId();
    }

    @Test
    public void testPostWasDeletedFromPage() {
        ModifyResponse deleteResponse = getFbMethods().deletePost(createdPostId, getSettings().getAccessToken());
        assertThat(deleteResponse.getSuccess()).as("The post should be deleted with success").isTrue();
        FeedResponse getFeedResponse = getFbMethods().getPageFeed(getSettings().getPageId(), getSettings().getAccessToken());
        assertThat(getFeedResponse.getStatusCode()).as("Status is not OK").isEqualTo(200);
        assertThat(getFeedResponse.getPostData()).as("Feed responses should not contain the id of updated post").doesNotContain(new PostData(postMessage, createdPostId));
    }


}
