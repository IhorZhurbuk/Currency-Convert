import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ExchangeClient {
    private final static String KEY = "fe8264153bf0eddebcd5";

    public static List<String> currency() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder url = new URIBuilder()
                .setScheme("https")
                .setHost("free.currconv.com")
                .setPath("api/v7/currencies")
                .addParameter("apiKey", KEY);

        HttpGet httpGet = new HttpGet(url.toString());
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String jsonString = EntityUtils.toString(response.getEntity());

        System.out.println(jsonString);

        try {
            JSONObject parseObject = new JSONObject(jsonString);
            JSONObject temp = parseObject.getJSONObject("results");
            JSONArray tempArray = temp.names();

            List<String> list = new ArrayList<>();
            for (Object itr : tempArray
            ) {
                list.add(temp.getJSONObject(itr.toString()).getString("id"));

            }
            return list;
        } catch (JSONException exception) {
            System.out.println(exception.toString());
        }
        return null;
    }

    public static double currencyExchange(String fromAmount, String to, String amount) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder url = new URIBuilder()
                .setScheme("https")
                .setHost("free.currconv.com")
                .setPath("/api/v7/convert")
                .addParameter("q", fromAmount + "_" + to)
                .setParameter("compact", "ultra")
                .addParameter("apiKey", KEY);

        HttpGet httpGet = new HttpGet(url.toString());

        CloseableHttpResponse response = httpClient.execute(httpGet);
        String jsonString = EntityUtils.toString(response.getEntity());
        try {
            JSONObject parsedObject = new JSONObject(jsonString);
            double d = parsedObject.getDouble(fromAmount + "_" + to);
            double result = d * Double.parseDouble(amount);
            return result;
        } catch (JSONException jsonException) {
            System.out.println(jsonException.toString());
        }

        return 0;
    }

}
