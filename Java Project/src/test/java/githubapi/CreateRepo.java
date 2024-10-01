package githubapi;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import githubapi.testing.PropertyReader;

import java.io.IOException;

public class CreateRepo {

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

    @Test(priority = 1)
    public void createRepo() throws Exception{

        HttpPost request = new HttpPost(PropertyReader.getProperty("base_url") + "/user/repos");
        request.setHeader(HttpHeaders.AUTHORIZATION, "token " + PropertyReader.getProperty("token"));

        String json = "{\"name\": \"hello-world2\"}";
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        System.out.println(json);

        response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 201);
    }

    @Test(priority = 2)
    public void deleteRepo() throws Exception{

        HttpDelete request = new HttpDelete("https://api.github.com/repos/Vladimir-Lutic/hello-world2");
        request.setHeader(HttpHeaders.AUTHORIZATION, "token " + PropertyReader.getProperty("token"));

        response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 204);


        response = client.execute(request);
        statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 404);

    }

}
