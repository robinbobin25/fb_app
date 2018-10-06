package fbApi.responses;

import fbApi.data.PostData;
import io.restassured.response.Response;

import java.util.List;

/**
 * Facebook returns this response
 * when request GET https://graph.facebook.com/page_id/feed
 */
public class FeedResponse extends FbResponse {

    private List<PostData> postData;

    public FeedResponse(Response response) {
        super(response);
        this.postData = response.jsonPath().getList("data.", PostData.class);
    }

    public List<PostData> getPostData() {
        return postData;
    }

}