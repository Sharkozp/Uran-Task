package an.dikij.androidtask.app;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import an.dikij.androidtask.app.custom.ConnectionClass;
import an.dikij.androidtask.app.custom.CustomCursorAdapter;
import an.dikij.androidtask.app.custom.CustomOnItemClickListener;
import an.dikij.androidtask.app.custom.DBHelper;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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
				
		ListView dataList = (ListView) findViewById(R.id.dataList);
		String[] from = new String[] { "_id", "request" };
		int[] to = new int[] { R.id.idText, R.id.itemText };

		db = new DBHelper(this);
		adapter = new CustomCursorAdapter(this, R.layout.item,
				db.getAllRecords(), from, to, 0);		
		adapter.setViewBinder(new CustomCursorAdapter.ViewBinderImpl());
		dataList.setAdapter(adapter);
		dataList.setOnItemClickListener(new CustomOnItemClickListener(this));
		
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
			long date = new Date().getTime();
			String response = this.getResponse(date);
			db = new DBHelper(this);
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
	
	private String getResponse(long date) {
		ConnectionClass connectionClass = new ConnectionClass();
		connectionClass.execute(date);
		String response = null;
		try {
			response = connectionClass.get();
		} catch (InterruptedException e) {
			Log.e("UTException", e.toString());
		} catch (ExecutionException e) {
			Log.e("UTException", e.toString());
		}
		return response;
	}
}