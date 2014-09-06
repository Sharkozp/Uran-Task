package an.dikij.androidtask.app;

import java.util.Date;
import java.util.Random;

import an.dikij.androidtask.app.custom.ConnectionClass;
import an.dikij.androidtask.app.custom.CustomCursorAdapter;
import an.dikij.androidtask.app.custom.CustomOnItemClickListener;
import an.dikij.androidtask.app.custom.DBHelper;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by Oleksandr Dykyi.
 */
public class Main extends ActionBarActivity implements View.OnClickListener {
	private static final int MAX_COLOR = 255;
	private Random gen = new Random();
	private ConnectionClass connectionClass = new ConnectionClass();
	private RelativeLayout myLayout;
	private SimpleCursorAdapter adapter;
	private DBHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		myLayout = (RelativeLayout) findViewById(R.id.myLayout);
		Button changeButton = (Button) findViewById(R.id.changeButton);

		changeButton.setOnClickListener(this);
		db = new DBHelper(this);
		ListView dataList = (ListView) findViewById(R.id.dataList);
		String[] from = new String[] { "_id", "request" };
		int[] to = new int[] { R.id.idText, R.id.itemText };		

		adapter = new CustomCursorAdapter(this, R.layout.item,
				db.getAllRecords(), from, to, 0);

		adapter.setViewBinder(new CustomCursorAdapter.ViewBinderImpl());		
		dataList.setAdapter(adapter);

		Button downloadButton = (Button) findViewById(R.id.downloadButton);
		downloadButton.setOnClickListener(this);
		dataList.setOnItemClickListener(new CustomOnItemClickListener(this));
		db.close();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.changeButton:
			myLayout.setBackgroundColor(Color.rgb(gen.nextInt(MAX_COLOR),
					gen.nextInt(MAX_COLOR), gen.nextInt(MAX_COLOR)));
			break;
		case R.id.downloadButton:
			db = new DBHelper(this);
			long date = new Date().getTime();

			String response = connectionClass.connect(date);
			db.addData(date, response);			
			db.close();
			this.onResume();
			break;
		}
	}

	@Override
	protected void onResume() {
		Cursor cursor = db.getAllRecords();
		adapter.changeCursor(cursor);
		db.close();
		super.onResume();
	}
}