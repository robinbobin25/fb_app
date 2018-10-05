package utils;

import java.util.Properties;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
public class TestSettings {

    private String accessToken;
    private String pageId;

    public TestSettings(Properties properties) {
        this.accessToken = properties.getProperty("page_access_token");
        this.pageId = properties.getProperty("page_id");
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getPageId() {
        return pageId;
    }
}
