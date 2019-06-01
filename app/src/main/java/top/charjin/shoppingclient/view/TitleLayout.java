package top.charjin.shoppingclient.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import top.charjin.shoppingclient.R;

public class TitleLayout extends LinearLayout {

    private String title;

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_toolbar_back_title, this);

        ImageView ivBack = findViewById(R.id.iv_back);
        TextView tvTitle = findViewById(R.id.tv_title);

        ivBack.setOnClickListener(v -> ((Activity) context).finish());
        tvTitle.setText(title);

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
