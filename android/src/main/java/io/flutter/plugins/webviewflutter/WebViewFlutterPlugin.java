package io.flutter.plugins.webviewflutter;

import android.content.Context;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

/** WebViewFlutterPlugin */
public class WebViewFlutterPlugin {

  static class WebViewFactory extends PlatformViewFactory {
    private final BinaryMessenger messenger;

    public WebViewFactory(BinaryMessenger messenger) {
      super(StandardMessageCodec.INSTANCE);
      this.messenger = messenger;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlatformView create(Context context, int id, Object args) {
      Map<String, Object> params = (Map<String, Object>) args;
      return new FlutterWebView(context, messenger, id, params);
    }
  }

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    registrar.platformViewRegistry()
        .registerViewFactory("plugins.flutter.io/webview", new WebViewFactory(registrar.messenger()));
  }

}
