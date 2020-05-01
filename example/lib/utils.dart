import 'package:flutter/foundation.dart';

printIfDebug(data) {
  if (!kReleaseMode) print(data);
}
