package bookstore;

import bookstore.testing.BookstoreUtility;
import bookstore.testing.dto.BookstoreUser;
import bookstore.testing.dto.TokenUser;
import githubapi.testing.PropertyReader;
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

import java.io.IOException;

public class CreateUser {

    CloseableHttpClient client;
    CloseableHttpResponse response;
    String ID = "";
    String token = "";
    String userName = "abcdefgh123456";
    String password = "Aa123456789!";

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
    public void createUser() throws Exception {

        String json = "{\"userName\": " + "\"" + userName + "\", \"password\": " + "\"" + password +"\"}";

        HttpPost request = new HttpPost(PropertyReader.getProperty("bookstore_url") + PropertyReader.getProperty("create_user_endpoint"));
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        response = client.execute(request);

        BookstoreUser user = BookstoreUtility.deserializeBookstoreUser(response, BookstoreUser.class);

        ID = user.getUserID();

        //System.out.println(EntityUtils.toString(response.getEntity()));
        // TODO How do I print the response body in the test results?
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 201);

    }

    @Test(priority = 2)
    public void generateToken() throws Exception {

        String json = "{\"userName\": " + "\"" + userName + "\", \"password\": " + "\"" + password +"\"}";

        HttpPost request = new HttpPost(PropertyReader.getProperty("bookstore_url") + PropertyReader.getProperty("generate_token_endpoint"));
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        response = client.execute(request);

        TokenUser user = BookstoreUtility.deserializeTokenUser(response, TokenUser.class);

        token = user.getToken();
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

    }

    @Test(priority = 4)
    public void deleteUser() throws Exception{

        HttpDelete request = new HttpDelete(PropertyReader.getProperty("bookstore_url") + PropertyReader.getProperty("create_user_endpoint") + "/" + ID);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        response = client.execute(request);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

    }
}
