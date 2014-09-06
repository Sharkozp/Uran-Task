package an.dikij.androidtask.app.system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Oleksandr Dykyi.
 */
public class DBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "myDB";
	private static final String TABLE_NAME = "mytable";
	private static final String SQL_STRING = "create table mytable(_id INTEGER PRIMARY KEY AUTOINCREMENT, request datetime DEFAULT CURRENT_TIMESTAMP, response TEXT);";
	private SQLiteDatabase db;
	private ContentValues values;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	
	/**
	 * Create database table with rows
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_STRING);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	/**
	 * Add new row in table
	 * @param date
	 * @param response
	 */
	public void addData(long date, String response) {
		db = this.getWritableDatabase();
		values = new ContentValues();
		values.put("request", date);
		values.put("response", response);
		db.insert(TABLE_NAME, null, values);
		db.close();
	}

	/**
	 * Return all saved data from the table
	 * @return @see Cursor
	 */
	public Cursor getAllRecords() {
		db = this.getWritableDatabase();
		String query = "SELECT  * FROM mytable Order BY _id DESC";
		Cursor cursor = db.rawQuery(query, null);
		
		return cursor;
	}

	/**
	 * Return saved record by id from the table
	 * @return @see Cursor
	 */
	public Cursor getRecordById(long id) {
		db = this.getWritableDatabase();
		String query = "SELECT  * FROM mytable where _id = ?";
		Cursor cursor = db.rawQuery(query, new String[] { String.valueOf(id) });
		
		return cursor;
	}
}
