package za.co.smileyjoedev.cashregister.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import za.co.smileyjoedev.cashregister.R;
import za.co.smileyjoedev.cashregister.adapter.ItemListAdapter;
import za.co.smileyjoedev.cashregister.db.DbItemAdapter;
import za.co.smileyjoedev.cashregister.object.Item;

/**
 * Created by cody on 2015/04/05.
 */
public class PayActivity extends BaseActivity {

    public static final String EXRTA_ITEMS = "items";
    public static final String EXRTA_QUANTITIES = "quantities";

    private TextView mTextTotal;
    private TextView mTextChange;
    private EditText mEditPayment;
    private ItemListAdapter mItemListAdapter;
    private HashMap<Long, Integer> mItemQuantities;
    private ArrayList<Item> mItems;
    private float mTotal = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        mTextTotal = (TextView) findViewById(R.id.text_total);
        mTextChange = (TextView) findViewById(R.id.text_change);
        mEditPayment = (EditText) findViewById(R.id.edit_payment);
        mItemQuantities = new HashMap<>();
        mItems = new ArrayList<>();
        ListView listItems = (ListView) findViewById(R.id.list_item);
        mItemListAdapter = new ItemListAdapter(getBaseContext(), mItems);
        mItemListAdapter.setListener(new ListAdapterListener());
        listItems.setAdapter(mItemListAdapter);

        Bundle extras = getIntent().getExtras();

        if(extras.containsKey(EXRTA_ITEMS) && extras.containsKey(EXRTA_ITEMS)){

            long[] ids = extras.getLongArray(EXRTA_ITEMS);
            int[] quantities = extras.getIntArray(EXRTA_QUANTITIES);

            for(int i = 0; i < ids.length; i++){
                if(quantities[i] > 0){
                    mItemQuantities.put(ids[i], quantities[i]);
                }
            }

            DbItemAdapter adapter = new DbItemAdapter(getBaseContext());
            mItems = adapter.getByIds(ids);

            mItemListAdapter.setQuantities(mItemQuantities);
            mItemListAdapter.update(mItems);

            setTotal();
        }
    }

    private void setTotal(){
        for(Item item:mItems){
            int quantity = mItemQuantities.get(item.getId());

            mTotal += quantity*item.getPrice();
        }

        mTextTotal.setText(Float.toString(mTotal));
    }

    private class ListAdapterListener implements ItemListAdapter.Listener{
        @Override
        public void totalIncrease(float amount) {
            mTotal += amount;
            mTextTotal.setText(Float.toString(mTotal));
        }

        @Override
        public void totalDecrease(float amount) {
            mTotal -= amount;
            mTextTotal.setText(Float.toString(mTotal));
        }
    }
}
