package za.co.smileyjoedev.cashregister.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import za.co.smileyjoedev.cashregister.R;
import za.co.smileyjoedev.cashregister.object.Item;

/**
 * Created by cody on 2015/04/04.
 */
public class ItemListAdapter extends BaseAdapter {

    public interface Listener{
        public void totalIncrease(float amount);
        public void totalDecrease(float amount);
    }

    private ArrayList<Item> mItems;
    private Context mContext;
    private HashMap<Long, Integer> mQuantities;
    private Listener mListener;

    public ItemListAdapter(Context context, ArrayList<Item> mItems) {
        mContext = context;
        this.mItems = mItems;
        mQuantities = new HashMap<>();
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    public void update(ArrayList<Item> items){
        mItems = items;
        notifyDataSetChanged();
    }

    public void setQuantities(HashMap<Long, Integer> quantities){
        mQuantities = quantities;
    }

    public HashMap<Long, Integer> getQuantities(){
        return mQuantities;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        Item item = getItem(position);

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_item_item, null);

            holder = new ViewHolder();

            holder.textPrice = (TextView) convertView.findViewById(R.id.text_price);
            holder.textTitle = (TextView) convertView.findViewById(R.id.text_title);
            holder.textTotal = (TextView) convertView.findViewById(R.id.text_total);
            holder.textQuantity = (TextView) convertView.findViewById(R.id.text_quantity);
            holder.buttonAdd = (Button) convertView.findViewById(R.id.button_add);
            holder.buttonSubtract = (Button) convertView.findViewById(R.id.button_subtract);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textTitle.setText(item.getTitle());
        holder.textPrice.setText(Float.toString(item.getPrice()));
        holder.buttonAdd.setOnClickListener(new OnAddClick(item));
        holder.buttonSubtract.setOnClickListener(new OnSubtractClick(item));

        if(mQuantities.containsKey(item.getId())){
            holder.textQuantity.setText(Integer.toString(mQuantities.get(item.getId())));

            holder.textTotal.setText(Float.toString(item.getPrice()*mQuantities.get(item.getId())));
        }

        return convertView;
    }

    public int getQuantity(Long itemId){
        if(mQuantities.containsKey(itemId)){
            return mQuantities.get(itemId);
        } else {
            return 0;
        }
    }

    private abstract class OnQuantityClick implements View.OnClickListener{

        protected Item mItem;

        protected abstract int editQuantity(int quantity);

        private OnQuantityClick(Item item) {
            mItem = item;
        }

        @Override
        public void onClick(View v) {
            int quantity = getQuantity(mItem.getId());
            quantity = editQuantity(quantity);
            mQuantities.put(mItem.getId(), quantity);
            notifyDataSetChanged();
        }
    }

    private class OnSubtractClick extends OnQuantityClick{

        private OnSubtractClick(Item item) {
            super(item);
        }

        @Override
        protected int editQuantity(int quantity) {
            if(quantity == 0){
                return 0;
            } else {
                if(mListener != null){
                    mListener.totalDecrease(mItem.getPrice());
                }
                return quantity-1;
            }
        }
    }

    private class OnAddClick extends OnQuantityClick{

        private OnAddClick(Item item) {
            super(item);
        }

        @Override
        protected int editQuantity(int quantity){
            if(mListener != null){
                mListener.totalIncrease(mItem.getPrice());
            }
            return quantity + 1;
        }
    }

    private class ViewHolder{
        public TextView textTitle;
        public TextView textPrice;
        public TextView textTotal;
        public Button buttonAdd;
        public Button buttonSubtract;
        public TextView textQuantity;
    }
}
