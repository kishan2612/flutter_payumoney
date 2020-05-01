import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:payumoney_plugin/payumoney_plugin.dart';
import 'package:payumoney_plugin_example/utils.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  Map<String, dynamic> payu_info = {};

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Row(
            children: <Widget>[
              Expanded(
                child: FlatButton(
                  child: Text(
                    "open payumoney",
                  ),
                  color: Colors.blue,
                  onPressed: () {
                    _openPayUMoney();
                  },
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Future<Null> _openPayUMoney() async {
    dynamic result;
    try {
      result = await PayumoneyPlugin.openPayUMoney(payu_info);
      printIfDebug("plaform result $result");
      if (result != null) {
        Map<String, dynamic> paymentResponse = new Map<String, dynamic>.from(
            Platform.isAndroid ? json.decode(result) : result);
        String status = paymentResponse['result']['status'];
        printIfDebug(status);
        if (status == 'success') {
          printIfDebug("completed");
        } else {
          printIfDebug("Failed");
        }

      } else {}
    } on PlatformException catch (e) {
      printIfDebug(e.toString());
    }
  }
}
