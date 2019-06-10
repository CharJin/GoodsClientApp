package top.charjin.shoppingclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import top.charjin.shoppingclient.R;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_main);

        etSearchContent = findViewById(R.id.ev_search_content);

        etSearchContent.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                searchGoods(etSearchContent);
                return true;
            }
            return false;
        });

        etSearchContent.requestFocus();

    }

    public void backHome(View view) {
        this.finish();
    }

    public void searchGoods(View view) {
        String key = etSearchContent.getText().toString().trim();
        if (key.length() == 0) {
            key = etSearchContent.getHint().toString();
        }
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }


}
