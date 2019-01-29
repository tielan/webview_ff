package io.flutter.plugins.webviewflutter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.platform.PlatformView;
import wendu.dsbridge.DWebView;

import java.util.HashMap;
import java.util.Map;

public class FlutterWebView implements PlatformView, MethodCallHandler {
  private final WebView webView;
  public MethodChannel methodChannel;

  @SuppressWarnings("unchecked")
  FlutterWebView(Context context, BinaryMessenger messenger, int id, Map<String, Object> params) {
    webView = new DWebView(context);
    if (params.containsKey("initialUrl")) {
      String url = (String) params.get("initialUrl");
      webView.loadUrl(url);
    }

    methodChannel = new MethodChannel(messenger, "plugins.flutter.io/webview_" + id);
    methodChannel.setMethodCallHandler(this);
    webView.setWebViewClient(browserClient);
    webView.setWebChromeClient(browserChromeClient);
  }

  @Override
  public View getView() {
    return webView;
  }

  @Override
  public void onMethodCall(MethodCall methodCall, Result result) {
    new FlutterMethodCall().onMethodCall(webView,methodCall,result);
  }

  private WebChromeClient browserChromeClient = new WebChromeClient() {
    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
      WebView newWebView = new DWebView(view.getContext());
      newWebView.setWebViewClient(browserClient);
      WebView.WebViewTransport transport = ((WebView.WebViewTransport)resultMsg.obj);
      transport.setWebView(newWebView);
      resultMsg.sendToTarget();
      return true;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
      super.onReceivedTitle(view, title);
      Map<String, Object> data = new HashMap<>();
      data.put("title", title);
      methodChannel.invokeMethod("onReceivedTitle", data);
    }
  };


  private WebViewClient browserClient = new WebViewClient() {

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
      super.onPageStarted(view, url, favicon);
      Map<String, Object> data = new HashMap<>();
      data.put("url", url);
      methodChannel.invokeMethod("onPageStarted", data);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      Map<String, Object> data = new HashMap<>();
      data.put("url", url);
      methodChannel.invokeMethod("onUrlChanged", data);
      methodChannel.invokeMethod("onPageFinished", data);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
      super.onReceivedError(view, errorCode, description, failingUrl);
      Map<String, Object> data = new HashMap<>();
      data.put("url", failingUrl);
      data.put("code", errorCode);
      methodChannel.invokeMethod("onReceivedError", data);
    }

    @TargetApi(21)
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
      super.onReceivedHttpError(view, request, errorResponse);
      Map<String, Object> data = new HashMap<>();
      data.put("url", request.getUrl().toString());
      data.put("code", Integer.toString(errorResponse.getStatusCode()));
      methodChannel.invokeMethod("onReceivedError", data);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
      super.onReceivedSslError(view, handler, error);
      handler.proceed();
    }

    //url 重写
    @TargetApi(21)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
      Map<String, Object> data = new HashMap<>();
      data.put("url", request.getUrl().toString());
      methodChannel.invokeMethod("shouldOverrideUrlLoading", data);
      return true;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
      Map<String, Object> data = new HashMap<>();
      data.put("url", url);
      methodChannel.invokeMethod("shouldOverrideUrlLoading", data);
      return true;
    }


  };

  @Override
  public void dispose() {

  }
}
