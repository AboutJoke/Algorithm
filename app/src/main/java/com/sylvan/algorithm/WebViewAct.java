package com.sylvan.algorithm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.ycbjie.webviewlib.InterWebListener;
import com.ycbjie.webviewlib.WebProgress;
import com.ycbjie.webviewlib.X5WebUtils;
import com.ycbjie.webviewlib.X5WebView;

/**
 * @ClassName: com.sylvan.algorithm
 * @Author: sylvan
 * @Date: 19-11-26
 */
public class WebViewAct extends Activity {
    private X5WebView mWebView;
    private WebProgress mProgress;
    private static final String BASE_URL = "https://github.com/CyC2018/CS-Notes/blob/master/notes/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
    }

    public static void openAct(Context context, String url) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, WebViewAct.class);
        intent.putExtra("title", url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void initView() {
        mWebView = (X5WebView) findViewById(R.id.web_view);
        mProgress = (WebProgress) findViewById(R.id.progress);

        mProgress.show();
        mProgress.setColor(this.getResources().getColor(R.color.colorAccent), this.getResources().getColor(R.color.colorPrimaryDark));

        String title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            String url = BASE_URL + title + ".md";
            mWebView.loadUrl(url);
        }

        mWebView.getX5WebChromeClient().setWebListener(interWebListener);
        mWebView.getX5WebViewClient().setWebListener(interWebListener);
    }

    private InterWebListener interWebListener = new InterWebListener() {
        @Override
        public void hindProgressBar() {
            mProgress.hide();
        }

        @Override
        public void showErrorView(@X5WebUtils.ErrorType int type) {
            switch (type) {
                //没有网络
                case X5WebUtils.ErrorMode.NO_NET:
                    break;
                //404，网页无法打开
                case X5WebUtils.ErrorMode.STATE_404:

                    break;
                //onReceivedError，请求网络出现error
                case X5WebUtils.ErrorMode.RECEIVED_ERROR:

                    break;
                //在加载资源时通知主机应用程序发生SSL错误
                case X5WebUtils.ErrorMode.SSL_ERROR:

                    break;
                case X5WebUtils.ErrorMode.STATE_500:

                    break;
                case X5WebUtils.ErrorMode.TIME_OUT:

                    break;
                default:
                    break;
            }
        }

        @Override
        public void startProgress(int newProgress) {
            mProgress.setWebProgress(newProgress);
        }

        @Override
        public void showTitle(String title) {

        }
    };
}
