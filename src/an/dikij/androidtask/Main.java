package an.dikij.androidtask;

import an.dikij.androidtask.app.R;
import an.dikij.androidtask.app.R.id;
import an.dikij.androidtask.app.R.layout;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Main extends ActionBarActivity implements View.OnClickListener {
	private static final int MAX_COLOR = 255;
	private Random gen = new Random();

	// private = new ArrayList<String>();
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
		// ArrayList<Integer> historyRequest = db.getAllRecords();
		ListView dataList = (ListView) findViewById(R.id.dataList);
		String[] from = new String[] { "_id", "request" };
		int[] to = new int[] { R.id.idText, R.id.itemText };
		adapter = new SimpleCursorAdapter(this, R.layout.item,
				db.getAllRecords(), from, to);

		dataList.setAdapter(adapter);

		Button downloadButton = (Button) findViewById(R.id.downloadButton);
		downloadButton.setOnClickListener(this);
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
			DBHelper db = new DBHelper(this);

			ConnectionClass connectionClass = new ConnectionClass();

			Timestamp timestamp = new Timestamp(new Date().getTime());
			connectionClass.connect(timestamp);

			db.addDate(timestamp);
			// db.close();
		//	adapter.notifyDataSetChanged();
			onResume();
			break;
		}
	}

	@Override
	protected void onResume() {		
		Cursor cursor = db.getAllRecords();
		adapter.changeCursor(cursor);

		super.onResume();
	}
}
/**/