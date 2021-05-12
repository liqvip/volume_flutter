#import "VolumeFlutterPlugin.h"
#if __has_include(<volume_flutter/volume_flutter-Swift.h>)
#import <volume_flutter/volume_flutter-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "volume_flutter-Swift.h"
#endif

@implementation VolumeFlutterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftVolumeFlutterPlugin registerWithRegistrar:registrar];
}
@end
