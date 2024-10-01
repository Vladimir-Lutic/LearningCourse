package bookstore;

import bookstore.testing.BookstoreUtility;
import com.sun.xml.internal.ws.message.StringHeader;
import githubapi.testing.PropertyReader;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddBook {

    CloseableHttpClient client;
    CloseableHttpResponse response;
    String userID = "ec218064-301d-489d-ab90-7e8af67e34c1";
    String userName = "Vladimir-Lutic-Test-User";
    String password = "Aa123456789!";
    String token = "";
    List<String> isbn = new ArrayList<>();

    @BeforeMethod
    public void setup(){

        client = HttpClientBuilder.create().build();
    }

    @BeforeMethod
    public void generateToken() throws Exception {
        token = BookstoreUtility.generateToken(userName, password);
    }

    @BeforeMethod
    public void fillTheList(){
        isbn.add("9781449325862");
        isbn.add("9781491904244");
    }

    @AfterMethod
    public void closeResource() throws IOException {
        client.close();
        response.close();
    }

    /*@DataProvider
    private Object[][] isbn() {
        return new Object[][]{
                {isbn.get(0)},
                {isbn.get(1)}
        };
    }*/

    @Test
    public void addBook() throws Exception {

        //TODO What's the best solution to store and pass multiple ISBNs?

        String json = "{\"userId\":\"" + userID + "\",\"collectionOfIsbns\":[{\"isbn\":\"" + isbn.get(0) + "\"},{\"isbn\":\"" + isbn.get(1) + "\"}]}";

        HttpPost request = new HttpPost(PropertyReader.getProperty("bookstore_url") + PropertyReader.getProperty("books_endpoint"));
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        response = client.execute(request);

        //String test = EntityUtils.toString(response.getEntity());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 201);

    }

    @Test
    public void deleteBooks(){

        //String json = "{\"userId\":\"" + userID + "\",\"isbn\":\"" + isbn + "\"}";

        //TODO HttpDelete should have a body as per Swagger

        HttpDelete request = new HttpDelete(PropertyReader.getProperty("bookstore_url") + PropertyReader.getProperty("books_endpoint"));
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 201);

    }

}
