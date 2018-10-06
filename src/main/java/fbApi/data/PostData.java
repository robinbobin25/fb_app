package fbApi.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class describes the node Data in the getFeed Response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostData {

    private String message;
    private String id;

    @JsonCreator
    public PostData(@JsonProperty("message") String message, @JsonProperty("id") String id) {
        this.message = message;
        this.id = id;
    }

    @Override
    public String toString() {
        return "PostData{" +
                "message='" + message + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostData postData = (PostData) o;

        if (message != null ? !message.equals(postData.message) : postData.message != null) return false;
        return id != null ? id.equals(postData.id) : postData.id == null;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
