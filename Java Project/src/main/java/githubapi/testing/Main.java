package githubapi.testing;


import com.thoughtworks.xstream.XStream;
import githubapi.testing.dto.ValCurs;
import githubapi.testing.dto.Valute;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(githubapi.testing.PropertyReader.getProperty("bnmUrl"));
        HttpResponse response = client.execute(request);

// Get the response
        BufferedReader rd = new BufferedReader
                (new InputStreamReader(
                        response.getEntity().getContent()));

        String line = "";
        String fullResponse = "";

        while ((line = rd.readLine()) != null) {
            fullResponse += line + "\r\n";
        }

       XStream xstream = new XStream();

        xstream.allowTypesByWildcard(new String[] {
                "githubapi.testing.dto.**"
        });

        xstream.processAnnotations(ValCurs.class);
        xstream.processAnnotations(Valute.class);

        xstream.addImplicitCollection(ValCurs.class, "valutes", Valute.class);
        ValCurs valCurs = (ValCurs) xstream.fromXML(fullResponse);

        ValuteUtility.validateNominal(valCurs);
        ValuteUtility.findRate(valCurs, "EUR");
        ValuteUtility.getAvg(valCurs);
        ValuteUtility.sortByValue(valCurs);
        ValuteUtility.convert(valCurs,"EUR");
    }
}
