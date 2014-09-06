package an.dikij.androidtask.app.custom;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Oleksandr Dykyi.
 */
public class CustomCursorAdapter extends SimpleCursorAdapter {

	public CustomCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flag) {
		super(context, layout, c, from, to, flag);
	}

	public static class ViewBinderImpl implements ViewBinder {

		/**
	  	 * Reformat request value from long to dateFormat
	  	 */
		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			// get request column
			if (columnIndex == 1) {
				TextView tv = (TextView) view;
				String date = cursor
						.getString(cursor.getColumnIndex("request"));

				// change long date to formated date
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