package an.dikij.androidtask.app.custom;

import an.dikij.androidtask.app.Data;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Created by Oleksandr Dykyi.
 */
public class CustomOnItemClickListener implements OnItemClickListener {
	private Context context;
	
	public CustomOnItemClickListener(Context context) {
		this.context = context;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {		
		this.changeScreen(Data.class, id);
	}
	
	private void changeScreen(Class<?> clazz, long id) {
		Intent intent = new Intent(this.context, clazz);
		
		//transmission id to next screen 
		intent.putExtra("id", id);
		Activity activity = (Activity) this.context;
		activity.startActivity(intent);
		activity.finish();
	}

}
