#import "VolumeControlPlugin.h"
#if __has_include(<volume_control/volume_control-Swift.h>)
#import <volume_control/volume_control-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "volume_control-Swift.h"
#endif

@implementation VolumeControlPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftVolumeControlPlugin registerWithRegistrar:registrar];
}
@end
