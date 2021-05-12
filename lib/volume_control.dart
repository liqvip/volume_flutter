
import 'dart:async';

import 'package:flutter/services.dart';

class VolumeControl {
  static const MethodChannel _channel = const MethodChannel('volume_control');

  /// 设置音量最大范围
  static Future<void> setMaxVol(double num) async{
    await _channel.invokeMethod("setMaxVol",num);
  }

  /// 获取当前音量
  static Future<double> getCurrentVol(AudioType audioType) async{
    return await _channel.invokeMethod("getCurrentVol",_getStreamInt(audioType)) as double;
  }

  /// 改变媒体音量
  static Future<double> changeMediaVoice(double num) async{
    return await _channel.invokeMethod("changeMediaVoice",num) as double;
  }

  /// 改变系统音量
  static Future<double> changeSysVoice(double num) async{
    return await _channel.invokeMethod("changeSysVoice",num) as double;
  }
}

enum AudioType {
  /// Controls the Voice Call volume
  STREAM_VOICE_CALL,

  /// Controls the system volume
  STREAM_SYSTEM,

  /// Controls the ringer volume
  STREAM_RING,

  /// Controls the media volume
  STREAM_MUSIC,

  // Controls the alarm volume
  STREAM_ALARM,

  /// Controls the notification volume
  STREAM_NOTIFICATION
}

int _getStreamInt(AudioType audioType) {
  switch (audioType) {
    case AudioType.STREAM_VOICE_CALL:
      return 0;
    case AudioType.STREAM_SYSTEM:
      return 1;
    case AudioType.STREAM_RING:
      return 2;
    case AudioType.STREAM_MUSIC:
      return 3;
    case AudioType.STREAM_ALARM:
      return 4;
    case AudioType.STREAM_NOTIFICATION:
      return 5;
    default:
      return null;
  }
}
