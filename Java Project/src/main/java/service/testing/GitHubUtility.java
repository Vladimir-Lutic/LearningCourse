package service.testing;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import service.testing.dto.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GitHubUtility {

    public static String getHeader(CloseableHttpResponse response, String headerName) {

        // Get All headers
        Header[] headers = response.getAllHeaders();
        List<Header> httpHeaders = Arrays.asList(headers);
        String returnHeader = "";

        // Loop over the headers list
        for(Header header : httpHeaders){
            if(headerName.equalsIgnoreCase(header.getName())){
                returnHeader = header.getValue();
            }
        }

        // If no header found - throw an exception
        if(returnHeader.isEmpty()){
            throw new RuntimeException("Didn't find the header: " + headerName);
        }

        // Return the header
        return returnHeader;
    }

    public static boolean headerIsPresent(CloseableHttpResponse response, String headerName){
        List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());

        return httpHeaders.stream()
                .anyMatch(header -> header.getName().equalsIgnoreCase(headerName));
    }

    public static User deserialize(CloseableHttpResponse response, Class <User> clazz) throws IOException {
        String jsonBody = EntityUtils.toString(response.getEntity());

        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(jsonBody, clazz);
    }

}
