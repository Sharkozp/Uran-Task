package an.dikij.androidtask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Oleksandr Dykyi.
 */
class DBHelper extends SQLiteOpenHelper {
	private static final String TABLE_NAME = "mytable";
	private static final String SQL_STRING = "create table mytable(_id INTEGER PRIMARY KEY AUTOINCREMENT, request datetime DEFAULT CURRENT_TIMESTAMP, response TEXT);";
	private SQLiteDatabase db;
	private ContentValues values;
	public DBHelper(Context context) {
		super(context, "myDB", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//create database table with rows
		db.execSQL(SQL_STRING);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void addData(long date, String response) {
		db = this.getWritableDatabase();
		values = new ContentValues();
		values.put("request", date);		
		values.put("response", response);
		db.insert(TABLE_NAME, null, values);
		db.close();
	}	

	public Cursor getAllRecords() {
		db = this.getWritableDatabase();
		String query = "SELECT  * FROM mytable Order BY _id DESC";		
		Cursor cursor = db.rawQuery(query, null);

		return cursor;
	}
}
