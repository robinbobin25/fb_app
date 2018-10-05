package fbApi;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.TestSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
abstract public class BaseApiTest {

    static HttpFbApiClient fbMethods;
    static String pageAccessToken;
    static String pageId;
    static String createdPostId;
    static String postMessage;

    private static TestSettings testSettings = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseApiTest.class.getSimpleName());

    @BeforeClass
    public static void setup() {
        loadTestSettings();
        fbMethods = new HttpFbApiClient();
        pageAccessToken = testSettings.getAccessToken();
        pageId = testSettings.getPageId();
        postMessage = generatePostMessage();
    }

    private static void loadTestSettings() {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream("src/test/resources/credentials.properties")) {
            properties.load(inputStream);
            testSettings = new TestSettings(properties);
        } catch (IOException | NullPointerException e) {
            LOGGER.error("Cannot load properties from specified path");
        }
    }

    private static String generatePostMessage() {
        int randomNum = new Random().nextInt(100) + 1;
        return "This message #" + randomNum + " is sent by Graph API";
    }


}
