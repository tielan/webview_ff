// Copyright 2018 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'dart:async';
import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';

void main() => runApp(MaterialApp(home: WebViewExample()));

class WebViewExample extends StatelessWidget {
   WebViewController _webViewController;

  void onItemClick(int i) async {
    if (await _webViewController.canGoBack()) {
      _webViewController.goBack();
    } else {
      print('back');
    }
    ;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Flutter WebView example'),
        elevation: 0.0,
      ),
      body: Column(
        children: <Widget>[
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: <Widget>[
              InkWell(
                onTap: () => onItemClick(1),
                child: Container(
                  height: 44.0,
                  child: Center(
                    child: Text('item1'),
                  ),
                ),
              ),
              InkWell(
                onTap: () => onItemClick(1),
                child: Container(
                  height: 44.0,
                  child: Center(
                    child: Text('item1'),
                  ),
                ),
              ),
              InkWell(
                onTap: () => onItemClick(1),
                child: Container(
                  height: 44.0,
                  child: Center(
                    child: Text('item1'),
                  ),
                ),
              ),
              InkWell(
                onTap: () => onItemClick(1),
                child: Container(
                  height: 44.0,
                  child: Center(
                    child: Text('item1'),
                  ),
                ),
              )
            ],
          ),
          Expanded(
              child: WebView(
                  initialUrl: 'https://www.baidu.com',
                  javascriptMode: JavascriptMode.unrestricted,
                  onEventChanged: (event){
                      print(event);
                  },
                  onWebViewCreated: (WebViewController webViewController) {
                    _webViewController = webViewController;
                    webViewController.addListener((){
                      print('object');
                      webViewController.loadUrl(webViewController.showOverrideUrl);
                    });
                  }))
        ],
      ),
    );
  }
}
