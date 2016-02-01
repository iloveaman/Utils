
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import java.lang.reflect.Field;

import in.srain.cube.util.CLog;

public class ScrollWebView extends WebView {
    public OnScrollChangeListener listener;

    public ScrollWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWebView(Context context) {
        super(context);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        super.onScrollChanged(l, t, oldl, oldt);

        float webcontent = getContentHeight() * getScale();// webview的高度
        float webnow = getHeight() + getScrollY();// 当前webview的高度
        //Log.i("TAG1", "webview.getScrollY()====>>" + getScrollY());
        if (Math.abs(webcontent - webnow) < 1) {
            // 已经处于底端
            // Log.i("TAG1", "已经处于底端");
            listener.onPageEnd(l, t, oldl, oldt);
        } else if (getScrollY() == 0) {
            // Log.i("TAG1", "已经处于顶端");
            listener.onPageTop(l, t, oldl, oldt);
        } else {
            listener.onScrollChanged(l, t, oldl, oldt);
        }

    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {

        this.listener = listener;

    }

    public interface OnScrollChangeListener {
        public void onPageEnd(int l, int t, int oldl, int oldt);

        public void onPageTop(int l, int t, int oldl, int oldt);

        public void onScrollChanged(int l, int t, int oldl, int oldt);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        CLog.i("ScrollWebView", "keyCode=" + keyCode);
        return super.onKeyDown(keyCode, event);
    }


    public void hideZoomController() {
        WebSettings websettings = getSettings();
        if (hideZoomByHoneycombApi(websettings)) {
            return;
        }
        hideZoomByReflection();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private boolean hideZoomByHoneycombApi(WebSettings websettings) {
        if (Build.VERSION.SDK_INT < 11) {
            return false;
        }
        websettings.setDisplayZoomControls(false);
        return true;
    }

    private void hideZoomByReflection() {
        Class<?> classType;
        Field field;
        try {
            classType = WebView.class;
            field = classType.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(this);
            mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
            try {
                field.set(this, mZoomButtonsController);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
