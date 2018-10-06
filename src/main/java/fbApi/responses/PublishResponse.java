package fbApi.responses;

import io.restassured.response.Response;

/**
 * Facebook returns this response
 * when request POST https://graph.facebook.com/page_id/feed?message=message
 */
public class PublishResponse extends FbResponse {

    private String id;

    public PublishResponse(Response response) {
        super(response);
        this.id = response.jsonPath().getString("id");
    }

    public String getPostId() {
        return id;
    }

}
