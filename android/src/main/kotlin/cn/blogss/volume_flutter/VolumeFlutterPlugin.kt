package cn.blogss.volume_flutter

import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** VolumeFlutterPlugin */
class VolumeFlutterPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  private lateinit var volumeManager: VolumeManager

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "volume_flutter")
    channel.setMethodCallHandler(this)

    volumeManager = VolumeManager(flutterPluginBinding.applicationContext)
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when(call.method){
      "setMaxVol" -> {    // 设置最大音量范围
        volumeManager.setMaxVol(call.arguments as Double);
      }
      "getCurrentVol" -> {    // 获取当前音量
        volumeManager.setAudioType(call.arguments as Int)
        result.success(volumeManager.currentVolume);
      }
      "changeMediaVoice" -> { // 改变媒体音量
        volumeManager.setAudioType(VolumeManager.TYPE_MUSIC)
        val curVoice = volumeManager.setVoice(call.arguments as Double);
        result.success(curVoice)
      }
      "changeSysVoice" -> {   //改变系统音量
        volumeManager.setAudioType(VolumeManager.TYPE_SYSTEM)
        val curVoice = volumeManager.setVoice(call.arguments as Double);
        result.success(curVoice)
      }
      else -> {
        result.notImplemented()
      }
    }
  }
}
