import 'dart:async';

import 'package:flutter/services.dart';

class PayumoneyPlugin {
  static const MethodChannel _channel =
      const MethodChannel('payumoney_plugin');

  static Future<dynamic> openPayUMoney(Map data) async {
     var result = await _channel.invokeMethod('getResult',data);
    return result;
  }
}
