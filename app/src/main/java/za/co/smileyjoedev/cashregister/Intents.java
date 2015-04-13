package za.co.smileyjoedev.cashregister;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import za.co.smileyjoedev.cashregister.activity.CreateItemActivity;
import za.co.smileyjoedev.cashregister.activity.PayActivity;

/**
 * Created by cody on 2015/04/04.
 */
public class Intents {

    public static final int INTENT_CREATE_ITEM = 1;
    public static final int INTENT_PAY = 2;

    public static Intent newItem(Context context){
        Intent intent = new Intent(context, CreateItemActivity.class);
        return intent;
    }

    public static Intent pay(Context context, HashMap<Long, Integer> itemQuantities){
        Intent intent = new Intent(context, PayActivity.class);

        long[] itemIds = new long[itemQuantities.size()];
        int[] quantities = new int[itemQuantities.size()];

        Iterator it = itemQuantities.entrySet().iterator();
        int count = 0;
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            itemIds[count] = (long) pair.getKey();
            quantities[count] = (int) pair.getValue();
            count++;
        }

        intent.putExtra(PayActivity.EXRTA_ITEMS, itemIds);
        intent.putExtra(PayActivity.EXRTA_QUANTITIES, quantities);

        return intent;
    }

}
