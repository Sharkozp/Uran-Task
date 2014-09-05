package an.dikij.androidtask.app.custom;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Oleksandr Dykyi.
 */
public class ConnectionClass {
    private HttpClient client;
    private HttpGet get;
    private static final String URL = "http://android-logs.uran.in.ua/test.php";

    public ConnectionClass() {
        HttpParams httpParameters = new BasicHttpParams();
        client = new DefaultHttpClient(httpParameters);
    }

    public String connect(long date) {
        StringBuilder result = new StringBuilder();
        StringBuilder newUrl = new StringBuilder();
        newUrl.append(URL).append("?").append("time=").append(date);

        get = new HttpGet(newUrl.toString());
        InputStream is = null;
        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                is = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));
                String temp = null;

                while ((temp = reader.readLine()) != null) {
                    result.append(temp);
                }
            }
        } catch (ClientProtocolException e) {
            Log.e("UTExseption", e.toString());
        } catch (IOException e) {
            Log.e("UTExseption", e.toString());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e("UTExseption", e.toString());
            }
        }
        return result.toString();
    }

}