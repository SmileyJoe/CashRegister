package za.co.smileyjoedev.cashregister.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import za.co.smileyjoedev.cashregister.Intents;
import za.co.smileyjoedev.cashregister.R;
import za.co.smileyjoedev.cashregister.adapter.ItemListAdapter;
import za.co.smileyjoedev.cashregister.db.DbItemAdapter;

/**
 * Created by cody on 2015/04/04.
 */
public class ItemListActivity extends BaseActivity {

    private ItemListAdapter mListAdapter;
    private DbItemAdapter mDbItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        mDbItemAdapter = new DbItemAdapter(getBaseContext());

        ListView list = (ListView) findViewById(R.id.list_item);
        mListAdapter = new ItemListAdapter(getBaseContext(), mDbItemAdapter.getAll());
        list.setAdapter(mListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                startActivityForResult(Intents.newItem(getBaseContext()), Intents.INTENT_CREATE_ITEM);
                return true;
            case R.id.action_pay:
                startActivityForResult(Intents.pay(getBaseContext(), mListAdapter.getQuantities()), Intents.INTENT_PAY);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Intents.INTENT_CREATE_ITEM:
                    mListAdapter.update(mDbItemAdapter.getAll());
                    break;
            }
        }

    }
}
