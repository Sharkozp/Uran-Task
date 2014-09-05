package an.dikij.androidtask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.Locale;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by sharko on 07.05.14.
 */
class DBHelper extends SQLiteOpenHelper {
	private static final String TABLE_NAME = "mytable";

	public DBHelper(Context context) {
		// конструктор суперкласса
		super(context, "myDB", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// создаем таблицу с полями
		db.execSQL("create table mytable(_id INTEGER PRIMARY KEY AUTOINCREMENT, request datetime DEFAULT CURRENT_TIMESTAMP);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void addDate(Timestamp date) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("request", getDateTime());
		db.insert(TABLE_NAME, null, values);
		db.close();
	}

	public Cursor getAllRecords() {

		String query = "SELECT  * FROM " + TABLE_NAME;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		return cursor;
	}
	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
}
}
