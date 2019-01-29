// Copyright 2018 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'dart:async';
import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';

void main() => runApp(MaterialApp(home: WebViewExample()));

class WebViewExample extends StatelessWidget {
  final Completer<WebViewController> _controller =
      Completer<WebViewController>();

  void onItemClick(int i) async {
    WebViewController webViewController = await _controller.future;
    if (await webViewController.canGoBack()) {
      webViewController.goBack();
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
                    _controller.complete(webViewController);
                  }))
        ],
      ),
    );
  }
}
