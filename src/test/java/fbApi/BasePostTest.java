package fbApi;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.TestSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

/**
 * Created by Antonina Mikhaylenko on 10/5/2018.
 */
abstract public class BasePostTest {

    private HttpFbApiClient fbMethods;
    private TestSettings testSettings = null;
    private final Logger LOGGER = LoggerFactory.getLogger(BasePostTest.class.getSimpleName());

    String postMessage;

    @Before
    public void setup() {
        loadTestSettings();
        fbMethods = new HttpFbApiClient();
        postMessage = generatePostMessage();
    }

    private void loadTestSettings() {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream("src/test/resources/credentials.properties")) {
            properties.load(inputStream);
            testSettings = new TestSettings(properties);
        } catch (IOException | NullPointerException e) {
            LOGGER.error("Cannot load properties from specified path");
        }
    }

    private String generatePostMessage() {
        int randomNum = new Random().nextInt(100) + 1;
        return "This message #" + randomNum + " is sent by Graph API";
    }

    TestSettings getSettings() {
        return testSettings;
    }

    HttpFbApiClient getFbMethods() {
        return fbMethods;
    }
}
