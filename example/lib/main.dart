import 'dart:async';

import 'package:flutter/material.dart';
import 'package:volume_flutter/volume_flutter.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  double _musicVoice;

  @override
  void initState() {
    super.initState();
    ///1.获取当前媒体音量
    initCurrentVol();
  }

  /// 获取当前媒体音量
  Future<void> initCurrentVol () async{
    _musicVoice = await VolumeFlutter.getCurrentVol(AudioType.STREAM_MUSIC);
    if(!mounted) return;
    setState(() {});
  }

  /// 改变媒体音量
  Future<void> changeMediaVoice(double vol) async{
    await VolumeFlutter.changeMediaVoice(vol);
    _musicVoice = vol;
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: (_musicVoice != null) ? Slider(
            value: _musicVoice,
            min: 0,
            max: 100,
            inactiveColor: Colors.grey,
            activeColor: Colors.blue,
            onChanged: (vol){
              /// 2. 滑动改变媒体音量
              changeMediaVoice(vol);
            },
          ): Container(),
        ),
      ),
    );
  }
}
