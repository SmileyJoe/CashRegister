package za.co.smileyjoedev.db;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;

public abstract class DbBase<T extends DbObject> {

	protected Context mContext;
	protected SQLiteDatabase mDb;
	protected DbHelper mDbHelper;
	protected Cursor mCursor;
	protected HashMap<String, Integer> mColumns;

	protected abstract String getTableName();

	protected abstract void setColoumns();

	protected abstract T parseData();

	protected abstract ContentValues createContentValues(T object);

	protected abstract String getQuery(String where);
	
	protected abstract T defaultObject();
	
	public DbBase(Context context) {
		mContext = context;
		mDbHelper = new DbHelper(context);
		mDb = mDbHelper.getWritableDatabase();
		mColumns = new HashMap<String, Integer>();
	}
	
	protected void setCursor(String where){
		mCursor = this.mDb.rawQuery(getQuery(where), null);
	}

	public T getDetails(long id) {
		return getByField("_id", Long.toString(id));
	}

	public T getByField(String fieldName, String value) {
		setCursor("WHERE " + fieldName + " = '" + value + "' ");
		return sortCursor();
	}
	
	public ArrayList<T> getByFieldArrayList(String fieldName, String value){
		setCursor("WHERE " + fieldName + " = '" + value + "' ");
		return sortCursorArrayList();
	}

	public boolean delete(long id) {
		int rowCount = mDb.delete(getTableName(), "_id = ?", getWhereArgs(id));
		return isSucces(rowCount);
	}

	public boolean update(T obj) {
		ContentValues values = createContentValues(obj);
		int rowCount = mDb.update(getTableName(), values, "_id = ?", getWhereArgs(obj.getId()));
		return isSucces(rowCount);
	}

	public long save(T obj) {
		long id = 0;
		if (obj.getId() <= 0) {
			ContentValues values = createContentValues(obj);
			id = mDb.insert(getTableName(), null, values);
			obj.setId(id);
		} else {
			update(obj);
			id = obj.getId();
		}
		return id;
	}

	protected String[] getWhereArgs(long id) {
		return new String[] { Long.toString(id) };
	}

	protected boolean isSucces(int rowAffectedCount) {
		if (rowAffectedCount > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<T> getAll() {
		return getAll("");
	}

	public ArrayList<T> getAll(String order) {
		setCursor(order);
		return sortCursorArrayList();
	}

	protected T sortCursor() {
		T obj = defaultObject();

		this.setColoumns();

		if (this.mCursor != null) {
			this.mCursor.moveToFirst();
			if (this.mCursor.getCount() > 0) {
				obj = parseData();
			}
		}

		return obj;
	}
	
	protected int getColumnCount(){
		return mCursor.getColumnCount();
	}

	protected ArrayList<T> sortCursorArrayList() {
		ArrayList<T> obj = new ArrayList<T>();

		this.setColoumns();

		if (this.mCursor != null) {
			this.mCursor.moveToFirst();
			if (this.mCursor.getCount() > 0) {
				do {
					obj.add(parseData());
				} while (this.mCursor.moveToNext());
			}
		}

		return obj;
	}
	
	protected void addColoumn(String name){
		mColumns.put(name, getColumnIndex(name));
	}
	
	protected String getColumnString(String name){
		return mCursor.getString(mColumns.get(name));
	}
	
	protected long getColumnLong(String name){
		return mCursor.getLong(mColumns.get(name));
	}
	
	protected int getColumnInt(String name){
		return mCursor.getInt(mColumns.get(name));
	}
	
	protected float getColumnFloat(String name){
		return mCursor.getFloat(mColumns.get(name));
	}
	
	protected double getColumnDouble(String name){
		return mCursor.getDouble(mColumns.get(name));
	}
	
	protected int getColumnIndex(String name){
		return mCursor.getColumnIndex(name);
	}
	
	protected boolean getColumnBoolean(String name){
		int value = getColumnInt(name);
		
		if(value == 1){
			return true;
		} else {
			return false;
		}
	}

    public ArrayList<T> getByIds(long[] ids){
        String where = "WHERE ";
        boolean first = true;

        for(long id:ids){
            if(!first){
                where += " OR ";
            } else {
                first = false;
            }
            where += Db.COL_ID + " = " + id;
        }

        setCursor(where);
        return sortCursorArrayList();
    }
}
