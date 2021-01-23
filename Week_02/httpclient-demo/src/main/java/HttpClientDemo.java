import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientDemo {

    public String getMessage(String url) throws IOException {
        CloseableHttpResponse response = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            response = client.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } finally {
            if (response != null)
                response.close();
        }
    }

    public static void main(String[] args) throws IOException {
        HttpClientDemo demo = new HttpClientDemo();
        String message = demo.getMessage("http://127.0.0.1:8801");
        System.out.println(message);
    }
}
