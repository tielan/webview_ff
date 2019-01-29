// Copyright 2018 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';

void main() => runApp(MaterialApp(home: Demo()));

class Demo extends StatefulWidget {
  @override
  _DemoState createState() => _DemoState();
}

class _DemoState extends State<Demo> {
  WebViewController _webViewController;
  String title1 = '';
  String title2 = '';
  void onEventChange(String method, Map params) {
    print(params);
    if (method == 'shouldOverrideUrlLoading') {
      _webViewController.loadUrl(params['url']);
    } else if (method == 'onReceivedTitle') {
      setState(() {
        title1 = params['title'];
      });
    } else if (method == 'onPageStarted') {
      setState(() {
        title2 = 'onPageStarted';
      });
    } else if (method == 'onPageFinished') {
      setState(() {
        title2 = 'onPageFinished';
      });
    } else if (method == 'onReceivedError') {
      setState(() {
        title2 = 'onReceivedError';
      });
    }
  }

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
                  width: 60.0,
                  child: Center(
                    child: Text(title1),
                  ),
                ),
              ),
              InkWell(
                onTap: () => onItemClick(1),
                child: Container(
                  height: 44.0,
                  child: Center(
                    child: Text(title2),
                  ),
                ),
              )
            ],
          ),
          Expanded(
              child: WebView(
                  initialUrl: 'http://www.ifeng.com/',
                  javascriptMode: JavascriptMode.unrestricted,
                  onWebViewCreated: (WebViewController webViewController) {
                    _webViewController = webViewController;
                    webViewController.addListener(() => onEventChange(
                        webViewController.method, webViewController.eventMap));
                  }))
        ],
      ),
    );
  }
}
