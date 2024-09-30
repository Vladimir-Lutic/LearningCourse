import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.testing.GitHubUtility;
import service.testing.PropertyReader;

import java.io.IOException;

public class TestResponseHeaders {

    CloseableHttpClient client;
    CloseableHttpResponse response;

    @BeforeMethod
    public void setup(){

        client = HttpClientBuilder.create().build();
    }

    @AfterMethod
    public void closeResource() throws IOException {
        client.close();
        response.close();
    }

    @Test
    public void verifyResponseHeaders() throws Exception{

        HttpGet request = new HttpGet(PropertyReader.getProperty("base_url"));
        response = client.execute(request);

        String headerValue = GitHubUtility.getHeader(response, "Server");

        Assert.assertEquals(headerValue, "github.com");

    }

    @Test
    public void headerIsPresent() throws Exception{

        HttpGet request = new HttpGet(PropertyReader.getProperty("base_url"));
        response = client.execute(request);

        Boolean headerIsPresent = GitHubUtility.headerIsPresent(response, "ETag");

        Assert.assertTrue(headerIsPresent);
    }
}
