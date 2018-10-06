package fbApi;

import fbApi.data.PostData;
import fbApi.responses.FeedResponse;
import fbApi.responses.ModifyResponse;
import fbApi.responses.PublishResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
public class TestUpdatePost extends BasePostTest {

    private final String POST_UPDATE_MESSAGE = "This message is updated by Graph API";
    private String createdPostId;

    @Before
    public void publishNewPostToPage() {
        PublishResponse publishResponse = getFbMethods().createPost(getSettings().getPageId(), postMessage, getSettings().getAccessToken());
        assertThat(publishResponse.getStatusCode()).as("Status is not OK").isEqualTo(200);
        createdPostId = publishResponse.getPostId();
    }

    @Test
    public void testPageFeedHasUpdatedPostWithSentData() {
        ModifyResponse updateResponse = getFbMethods().updatePost(createdPostId, POST_UPDATE_MESSAGE, getSettings().getAccessToken());
        assertThat(updateResponse.getStatusCode()).as("Status is not OK").isEqualTo(200);
        assertThat(updateResponse.getSuccess()).isNotNull().isTrue();
        // then check that page feed was updated with sent data
        FeedResponse getFeedResponse = getFbMethods().getPageFeed(getSettings().getPageId(), getSettings().getAccessToken());
        assertThat(getFeedResponse.getStatusCode()).as("Status is not OK").isEqualTo(200);
        assertThat(getFeedResponse).as("Feed responses should have at least one post").hasNoNullFieldsOrProperties();
        assertThat(getFeedResponse.getPostData()).as("Feed responses should contain all data of the updated post")
                .containsOnlyOnce(new PostData(POST_UPDATE_MESSAGE, createdPostId));
    }

    @After
    public void deleteCreatedPosts() {
        ModifyResponse deleteResponse = getFbMethods().deletePost(createdPostId, getSettings().getAccessToken());
        assertThat(deleteResponse.getStatusCode()).as("Status is not OK").isEqualTo(200);
        assertThat(deleteResponse.getSuccess()).as("The post should be deleted").isTrue();
    }

}