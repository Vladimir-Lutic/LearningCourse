package bookstore.testing;

import bookstore.testing.dto.BookstoreUser;
import bookstore.testing.dto.TokenUser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import githubapi.testing.PropertyReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class BookstoreUtility {

    public static BookstoreUser deserializeBookstoreUser(CloseableHttpResponse response, Class <BookstoreUser> clazz) throws IOException {
        String jsonBody = EntityUtils.toString(response.getEntity());

        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(jsonBody, clazz);
    }

    public static TokenUser deserializeTokenUser(CloseableHttpResponse response, Class <TokenUser> clazz) throws IOException {
        String jsonBody = EntityUtils.toString(response.getEntity());

        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(jsonBody, clazz);
    }

    public static boolean dataIsPresent( BookstoreUser user){

        return !user.getBooks().isEmpty();
    }

    public static String generateToken(String userName, String password) throws Exception {

        CloseableHttpClient client = HttpClientBuilder.create().build();;

        String json = "{\"userName\": " + "\"" + userName + "\", \"password\": " + "\"" + password +"\"}";

        HttpPost request = new HttpPost(PropertyReader.getProperty("bookstore_url") + PropertyReader.getProperty("generate_token_endpoint"));
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        CloseableHttpResponse response = client.execute(request);

        TokenUser user = BookstoreUtility.deserializeTokenUser(response, TokenUser.class);

        String token = user.getToken();
        client.close();

        return token;
    }

}
