package za.co.smileyjoedev.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class Db {
	
	public static final String COL_ID = "_id";

	public static String updateStatement(String tableName){
		String statment = "DROP TABLE IF EXISTS %s";
		
		return String.format(statment, tableName);
	}
	
	public static String createStatement(String tableName, String... columns){
		String statment = "CREATE TABLE %s (%s integer primary key autoincrement %s)";
		
		String cols = "";
		
		for(String column : columns){
			cols += "," + column;
		}
		
		statment = String.format(statment, tableName, COL_ID, cols);
		
		return statment;
	}
	
	public static String dropTable(String table){
		String statement = "DROP TABLE IF EXISTS %s";
		
		statement = String.format(statement, table);
		
		return statement;
	}
	
	public static String columnLong(String name){
		return column(name, "long");
	}
	
	public static String columnInt(String name){
		return column(name, "integer");
	}
	
	public static String columnString(String name){
		return column(name, "text");
	}

    public static String columnFloat(String name) {
        return column(name, "float");
    }
	
	private static String column(String name, String type){
		String column = "%s %s not null";
		
		return String.format(column, name, type);
	}
	
	public static int getCount(Context context, String table, String where){
		int count = 0;
		SQLiteDatabase db = new DbHelper(context).getWritableDatabase();
		
		String query = String.format("SELECT count(%s) FROM %s %s", Db.COL_ID, table, where);
		
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			
			count = cursor.getInt(cursor.getColumnIndex("count(" + Db.COL_ID + ")"));
		}
		
		return count;
	}

}
