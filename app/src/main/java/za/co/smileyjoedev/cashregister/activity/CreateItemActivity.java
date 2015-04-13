package za.co.smileyjoedev.cashregister.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import za.co.smileyjoedev.cashregister.R;
import za.co.smileyjoedev.cashregister.db.DbItemAdapter;
import za.co.smileyjoedev.cashregister.object.Item;

/**
 * Created by cody on 2015/04/04.
 */
public class CreateItemActivity extends BaseActivity {

    private EditText mEditTitle;
    private EditText mEditPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        mEditPrice = (EditText) findViewById(R.id.edit_price);
        mEditTitle = (EditText) findViewById(R.id.edit_title);

        Button buttonSave = (Button) findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new OnCreateClick());

    }

    private class OnCreateClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            DbItemAdapter adapter = new DbItemAdapter(v.getContext());
            Item item = new Item();

            item.setTitle(mEditTitle.getText().toString().trim());
            item.setPrice(Float.parseFloat(mEditPrice.getText().toString().trim()));

            if(adapter.save(item) > 0){
                mEditTitle.setText("");
                mEditPrice.setText("");
                setResult(Activity.RESULT_OK);
                finish();
            }

        }
    }
}
