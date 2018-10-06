package fbApi;

import fbApi.responses.FeedResponse;
import fbApi.responses.ModifyResponse;
import fbApi.responses.PublishResponse;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
public interface FbApiClient {

    PublishResponse createPost(String pageId, String postMessage, String accessToken);

    ModifyResponse updatePost(String postId, String updateMessage, String accessToken);

    ModifyResponse deletePost(String postId, String accessToken);

    FeedResponse getPageFeed(String pageId, String accessToken);
}
