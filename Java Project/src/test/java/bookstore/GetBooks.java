package bookstore;

import bookstore.testing.BookstoreUtility;
import bookstore.testing.dto.Books;
import bookstore.testing.dto.BookstoreUser;
import githubapi.testing.PropertyReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetBooks {

    CloseableHttpClient client;
    CloseableHttpResponse response;
    String ISBN = "9781449325862";

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
    public void getAllBooks() throws IOException {

        HttpGet request = new HttpGet(PropertyReader.getProperty("bookstore_url") + PropertyReader.getProperty("books_endpoint"));
        response = client.execute(request);

        BookstoreUser user = BookstoreUtility.deserializeBookstoreUser(response, BookstoreUser.class);
        Boolean booksReturned = BookstoreUtility.dataIsPresent(user);

        Assert.assertTrue(booksReturned);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

    }

    @Test
    public void getBook() throws IOException {

        HttpGet request = new HttpGet(PropertyReader.getProperty("bookstore_url") + "/BookStore/v1/Book?ISBN=" + ISBN);
        response = client.execute(request);

        BookstoreUser user = BookstoreUtility.deserializeBookstoreUser(response, BookstoreUser.class);
        Books book = new Books();

        //System.out.print(book.getTitle());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

    }

    @Test
    public void getBookNegative() throws IOException {

        HttpGet request = new HttpGet(PropertyReader.getProperty("bookstore_url") + "/BookStore/v1/Book?ISBN=DummyISBN");
        response = client.execute(request);

        BookstoreUser user = BookstoreUtility.deserializeBookstoreUser(response, BookstoreUser.class);
        Boolean booksReturned = BookstoreUtility.dataIsPresent(user);

        Assert.assertFalse(booksReturned);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 400);

    }

}
