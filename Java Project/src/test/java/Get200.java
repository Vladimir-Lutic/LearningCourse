import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class Get200 {

    CloseableHttpClient client;
    CloseableHttpResponse response;

    public static final String BASE_URL = "https://api.github.com";

    @BeforeMethod
    public void setup(){
        client = HttpClientBuilder.create().build();
    }

    @AfterMethod
    public void closeResource() throws IOException {
        // TODO Replace with closeable client
        client.close();
        response.close();
    }

    @Test(dataProvider = "endpoints")
    public void firstTest(String endpoint) throws IOException {

        HttpGet request = new HttpGet(BASE_URL + endpoint); // TODO Create config parameter with URL
        response = client.execute(request);

        int actualStatusCode = response.getStatusLine().getStatusCode();

        Assert.assertEquals(actualStatusCode, 200);
    }

    @DataProvider
    private Object[][] endpoints(){
        return new Object[][]{
                {""},
                {"/rate_limit"},
                {"/users/olgadarii"}
        };
    }

}