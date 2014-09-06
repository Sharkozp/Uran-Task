package an.dikij.androidtask.app.custom;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;
/**
 * Created by Oleksandr Dykyi.
 */
public class CustomActionBarActivity extends ActionBarActivity {
	private boolean exit = false;

	@Override
	public void onBackPressed() {
		if (exit) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} else {
			Toast.makeText(this, "Press Back again to Exit.",
					Toast.LENGTH_SHORT).show();
			exit = true;
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					exit = false;
				}
			}, 3 * 1000);
		}
	}
}
