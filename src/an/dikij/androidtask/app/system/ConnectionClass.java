package an.dikij.androidtask.app.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Oleksandr Dykyi.
 */
public class ConnectionClass extends AsyncTask<Long, Void, String> {
	private static final String URL_STRING = "http://android-logs.uran.in.ua/test.php";
	private Context context;

	public ConnectionClass(Context context) {
		super();
		this.context = context;
	}

	public String connect(long date) {
		StringBuilder result = new StringBuilder();
		StringBuilder newUrl = new StringBuilder();
		newUrl.append(URL_STRING).append("?").append("time=").append(date);

		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(newUrl.toString());
			urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));

			String temp = null;
			while ((temp = reader.readLine()) != null) {
				result.append(temp);
			}
		} catch (MalformedURLException e) {
			Log.e("UTException", e.toString());
		} catch (IOException e) {
			Log.e("UTException", e.toString());
		} finally {
			urlConnection.disconnect();
		}
		return result.toString();
	}

	@Override
	protected String doInBackground(Long... times) {
		for (Long time : times) {
			return this.connect(time);
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Toast.makeText(context, "Get new response!", Toast.LENGTH_SHORT).show();
	}
}
