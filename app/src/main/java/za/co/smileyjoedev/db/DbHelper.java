package za.co.smileyjoedev.db;

import za.co.smileyjoedev.cashregister.db.DbItemAdapter;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	
	public static final String DB_NAME_BASE = "cashregister";
	public static final int DB_VERSION = 1;
	
	public DbHelper(Context context){
		super(context, DB_NAME_BASE, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DbItemAdapter.create());
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL(DbItemAdapter.update(database));
	}
}
