import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.testing.PropertyReader;

import java.io.IOException;

public class Get404 {

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

    /*@DataProvider
    private Object[][] endpoints(){
        return new Object[][]{
                {"/user"},
                {"/user/followers"},
                {"/notifications"}
        };
    }*/

    @Test//(dataProvider = "endpoints")
    public void getBadRequest() throws Exception{

        HttpGet request = new HttpGet(PropertyReader.getProperty("base_url") + "/non_existent_endpoint");
        response = client.execute(request);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);

    }
}
