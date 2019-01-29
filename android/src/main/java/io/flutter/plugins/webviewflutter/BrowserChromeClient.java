package io.flutter.plugins.webviewflutter;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lejard_h on 20/12/2017.
 */

public class BrowserChromeClient extends WebChromeClient {

    public BrowserChromeClient() {
        super();
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        Map<String, Object> data = new HashMap<>();
        data.put("title", title);
        FlutterWebView.methodChannel.invokeMethod("onReceivedTitle", data);
    }
}