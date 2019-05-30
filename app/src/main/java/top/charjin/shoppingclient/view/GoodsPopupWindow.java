package top.charjin.shoppingclient.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import top.charjin.shoppingclient.R;

public class GoodsPopupWindow extends PopupWindow {

    private Context context;
    private View contentView;
    private AlphaHandler handler = new AlphaHandler();

    public GoodsPopupWindow(Context context, View contentView) {
        super(context);
        this.context = context;
        this.contentView = contentView;
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        setTouchable(true);
        setAnimationStyle(R.style.PopWindowBottom);
    }


    /**
     * 打开弹出窗口
     */
    public void openPopupWindow() {
        showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

        new Thread(() -> {
            float alpha = 1f;
            while (alpha > 0.5f) {
                try {
                    Thread.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                msg.obj = alpha;
                handler.sendMessage(msg);
                alpha -= 0.01f;
            }
        }).start();
    }


    @Override
    public void dismiss() {
        super.dismiss();
        closePopupWindow();

    }

    /**
     * 关闭弹出窗口
     */
    public void closePopupWindow() {
        new Thread(() -> {
            float alpha = 0.5f;
            while (alpha < 1f) {
                try {
                    Thread.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                msg.obj = alpha;
                handler.sendMessage(msg);
                alpha += 0.01f;
            }
        }).start();
    }


    @SuppressLint("HandlerLeak")
    class AlphaHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);

            Window window = ((Activity) context).getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.alpha = (float) msg.obj;
            window.setAttributes(layoutParams);
        }
    }
}
