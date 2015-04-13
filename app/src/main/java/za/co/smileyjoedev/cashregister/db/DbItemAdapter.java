package za.co.smileyjoedev.cashregister.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import za.co.smileyjoedev.cashregister.object.Item;
import za.co.smileyjoedev.db.Db;
import za.co.smileyjoedev.db.DbBase;

/**
 * Created by cody on 2015/04/04.
 */
public class DbItemAdapter extends DbBase<Item> {

    public static final String TABLE = "item";

    public static final String COL_ID = Db.COL_ID;
    public static final String COL_TITLE = TABLE + "_title";
    public static final String COL_PRICE = TABLE + "_price";

    public DbItemAdapter(Context context) {
        super(context);
    }

    public static String create(){
        return Db.createStatement(TABLE, Db.columnFloat(COL_PRICE), Db.columnString(COL_TITLE));
    }

    public static String update(SQLiteDatabase database){
        database.execSQL(Db.dropTable(TABLE));
        return create();
    }

    @Override
    protected String getTableName() {
        return TABLE;
    }

    @Override
    protected void setColoumns() {
        addColoumn(COL_ID);
        addColoumn(COL_TITLE);
        addColoumn(COL_PRICE);
    }

    @Override
    protected Item parseData() {
        Item item = defaultObject();

        item.setId(getColumnLong(COL_ID));
        item.setPrice(getColumnFloat(COL_PRICE));
        item.setTitle(getColumnString(COL_TITLE));

        return item;
    }

    @Override
    protected ContentValues createContentValues(Item item) {

        ContentValues values = new ContentValues();

        values.put(COL_PRICE, item.getPrice());
        values.put(COL_TITLE, item.getTitle());

        return values;
    }

    @Override
    protected String getQuery(String where) {

        String statement = "SELECT %s, %s, %s FROM %S %s ORDER BY %s ASC";

        String query = String.format(statement, COL_ID, COL_PRICE, COL_TITLE, TABLE, where, COL_TITLE);

        return query;
    }

    @Override
    protected Item defaultObject() {
        return new Item();
    }
}
