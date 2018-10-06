package fbApi.responses;

import io.restassured.response.Response;

/**
 * Created by Antonina on 06.10.2018.
 */
abstract public class FbResponse {

    private Response response;

    FbResponse(Response response) {
        this.response = response;
    }

    public int getStatusCode() {
        return response.statusCode();
    }

}
