package fbApi.responses;

import io.restassured.response.Response;

/**
 * Facebook returns this response
 * when request create/delete by POST https://graph.facebook.com/page_id/feed?message=message
 */
public class ModifyResponse extends FbResponse {

    private boolean success;

    public ModifyResponse(Response response) {
        super(response);
        this.success = response.jsonPath().getBoolean("success");
    }

    public boolean getSuccess() {
        return success;
    }

}
