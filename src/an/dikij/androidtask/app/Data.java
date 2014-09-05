package an.dikij.androidtask.app;

import an.dikij.androidtask.app.custom.DBHelper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Oleksandr Dykyi.
 */
public class Data extends ActionBarActivity {
	private SimpleCursorAdapter adapter;	
	private DBHelper db;
	private long id;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data);
		id = getIntent().getExtras().getLong("id");
		Button backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplication(), Main.class));
			}
		});
		
		db = new DBHelper(this);
		ListView dataList = (ListView) findViewById(R.id.dataDetail);
		String[] from = new String[] { "response" };
		int[] to = new int[] { R.id.dataText };		
		// TODO Change deprecated @see SimpleCursorAdapter
		adapter = new SimpleCursorAdapter(this, R.layout.datadetail,
				db.getRecord(id), from, to);
			
		dataList.setAdapter(adapter);
	}
}