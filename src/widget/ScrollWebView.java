
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
// /**
//      * 电视设置
//      */
//     private void tvSetting() {
//         //自适应任意大小的pc网页
//         WebSettings settings = webView.getSettings();
//         //settings.setTextSize(WebSettings.TextSize.NORMAL);
//         //得到文章字体的大小
//         int fontSize = 100;
//         settings.setTextZoom(fontSize);
//         //
//         //-------------------------------------------------
//         if (Build.VERSION.SDK_INT >= 8) {
//             settings.setPluginState(WebSettings.PluginState.ON);
//         } else {
//             //settings.setPluginsEnabled(true);
//         }
//         //
//         webView.setInitialScale(100);
//         //webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//         //webView.getSettings().setBlockNetworkImage(true);
//         // 设置可以支持缩放
//         settings.setSupportZoom(true);
//         // 设置出现缩放工具
//         settings.setBuiltInZoomControls(true);
//         //setZoomControlGone(webView);
//         //setZoomControlGone(webView);
//         webView.hideZoomController();
//         //扩大比例的缩放
//         settings.setUseWideViewPort(true);
//         //自适应屏幕
//         settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//         settings.setLoadWithOverviewMode(true);
//         //
//         webView.setVerticalScrollbarOverlay(false);
//         webView.setHorizontalScrollbarOverlay(false);
//         //去掉滚动条
//         webView.setVerticalScrollBarEnabled(false);
//         webView.setHorizontalScrollBarEnabled(false);
//         //
//         //-------------------------------------------------
//         //Java和Javascript安全交互
//         settings.setJavaScriptEnabled(true);// 支持js
//         webView.addJavascriptInterface(new JavaScriptInterface(), "androidlistner");
//         // 如果页面中链接，如果希望点击链接继续在当前browser中响应，而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
//         webView.setWebViewClient(new MyWebViewClient());
//         webView.setWebChromeClient(new WebChromeClient());
//         webView.requestFocus(View.FOCUS_DOWN | View.FOCUS_UP);
//         settings.setLightTouchEnabled(true);
//         webView.setOnScrollChangeListener(this);
//     }

//     /**
//      * 手机设置
//      */
//     private void phoneSetting() {
//         //自适应任意大小的pc网页
//         WebSettings settings = webView.getSettings();
//         settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//         settings.setUseWideViewPort(false);//;设置此属性，可任意比例缩放。
//         settings.setUseWideViewPort(false);
//         settings.setLoadWithOverviewMode(true);
//         if (Build.VERSION.SDK_INT >= 8) {
//             settings.setPluginState(WebSettings.PluginState.ON);
//         } else {
//             //settings.setPluginsEnabled(true);
//         }
//         //去掉滚动条
//         webView.setVerticalScrollBarEnabled(false);
//         webView.setHorizontalScrollBarEnabled(false);
//         int fontSize = 100;
//         settings.setTextZoom(fontSize);
//         webView.setInitialScale(100);
//         ZoomButtonsController zoom_controll;
//         //去掉缩放按钮
//         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//             // Use the API 11+ calls to disable the controls
//             settings.setBuiltInZoomControls(false);
//             settings.setDisplayZoomControls(false);
//         } else {
//             try {
//                 Class webview = Class.forName("android.webkit.WebView");
//                 Method method = webview.getMethod("getZoomButtonsController");
//                 zoom_controll = (ZoomButtonsController) method.invoke(this, true);
//                 zoom_controll.setZoomInEnabled(false);
//                 zoom_controll.setZoomOutEnabled(false);
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//         }
//         //Java和Javascript安全交互
//         settings.setJavaScriptEnabled(true);// 支持js
//         webView.addJavascriptInterface(new JavaScriptInterface(), "androidlistner");
//         // 如果页面中链接，如果希望点击链接继续在当前browser中响应，而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
//         webView.setWebViewClient(new MyWebViewClient());
//         webView.setWebChromeClient(new WebChromeClient());
//     }
