package io.flutter.plugins.webviewflutter;

import android.annotation.TargetApi;
import android.webkit.WebView;

import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class FlutterMethodCall {
    private WebView webView;
    public void onMethodCall(WebView webview, MethodCall methodCall, MethodChannel.Result result){
        this.webView = webview;
        switch (methodCall.method) {
            case "loadUrl":
                loadUrl(methodCall, result);
                break;
            case "canGoBack":
                canGoBack(methodCall, result);
                break;
            case "canGoForward":
                canGoForward(methodCall, result);
                break;
            case "goBack":
                goBack(methodCall, result);
                break;
            case "goForward":
                goForward(methodCall, result);
                break;
            case "reload":
                reload(methodCall, result);
                break;
            case "currentUrl":
                currentUrl(methodCall, result);
                break;
            case "evaluateJavascript":
                evaluateJavaScript(methodCall, result);
                break;
            default:
                result.notImplemented();
        }
    }


    private void loadUrl(MethodCall methodCall, MethodChannel.Result result) {
        String url = (String) methodCall.arguments;
        webView.loadUrl(url);
        result.success(null);
    }

    private void canGoBack(MethodCall methodCall, MethodChannel.Result result) {
        result.success(webView.canGoBack());
    }

    private void canGoForward(MethodCall methodCall, MethodChannel.Result result) {
        result.success(webView.canGoForward());
    }

    private void goBack(MethodCall methodCall, MethodChannel.Result result) {
        if (webView.canGoBack()) {
            webView.goBack();
        }
        result.success(null);
    }

    private void goForward(MethodCall methodCall, MethodChannel.Result result) {
        if (webView.canGoForward()) {
            webView.goForward();
        }
        result.success(null);
    }

    private void reload(MethodCall methodCall, MethodChannel.Result result) {
        webView.reload();
        result.success(null);
    }

    private void currentUrl(MethodCall methodCall, MethodChannel.Result result) {
        result.success(webView.getUrl());
    }


    @TargetApi(19)
    private void evaluateJavaScript(MethodCall methodCall, final MethodChannel.Result result) {
        String jsString = (String) methodCall.arguments;
        if (jsString == null) {
            throw new UnsupportedOperationException("JavaScript string cannot be null");
        }
        webView.evaluateJavascript(
                jsString,
                new android.webkit.ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        result.success(value);
                    }
                });
    }

}
