#demo:

```
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MineWebView mWv = new MineWebView(this);
        mWv.loadUrl("http://pedant.cn/self/cross-assets.html");
        setContentView(mWv);
    }



    protected class MineWebView extends WebView {
        private String mLoadUrl;

        public MineWebView(Context context) {
            super(context);
        }

        private Runnable mFetchRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    final String htmlStr = NetService.fetchHtml(mLoadUrl);
                    if (htmlStr != null) {
                        // 远程网页html代码中可以使用 file:///android_asset/...来访问apk本地资源
                        TaskExecutor.runTaskOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadDataWithBaseURL(mLoadUrl, htmlStr, "text/html", "UTF-8", "");
                                WebActivity.this.setTitle("加载完成");
                            }
                        });
                        return;
                    }
                } catch (Exception e) {
                    android.util.Log.e("MineWebView", "Exception:" + e.getMessage());
                }

                TaskExecutor.runTaskOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        android.util.Log.e("MineWebView", "fetch html failed");
                        // TODO 出错时可以处理自定义界面或其他事务 .....
                        WebActivity.this.setTitle("加载失败");
                        Toast.makeText(WebActivity.this, "fetch html failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        @Override
        public void loadUrl (String url) {
            mLoadUrl = url;
            WebActivity.this.setTitle("正在加载...");
            TaskExecutor.executeTask(mFetchRunnable);
        }
    }
```
