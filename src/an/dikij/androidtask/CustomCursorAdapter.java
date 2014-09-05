package an.dikij.androidtask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Oleksandr Dykyi.
 */
public class CustomCursorAdapter extends SimpleCursorAdapter {
	// TODO Change deprecated @see SimpleCursorAdapter
	@SuppressWarnings("deprecation")
	public CustomCursorAdapter(Context context, int layout, Cursor c, String[] from,
			int[] to) {
		super(context, layout, c, from, to);
	}

 static class ViewBinderImpl implements ViewBinder {		
		
		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			// get request column
			if (columnIndex == 1) { 
				TextView tv = (TextView) view;
				String date = cursor
						.getString(cursor.getColumnIndex("request"));
				
				//change long date to formated date
				tv.setText(this.formatData(date));
				return true;
			}
			return false;
		}

		private String formatData(String date) {
			long longDate = Long.parseLong(date);
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss", Locale.getDefault());

			return dateFormat.format(new Date(longDate));
		}		
	}
}