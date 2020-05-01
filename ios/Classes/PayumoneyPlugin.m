#import "PayumoneyPlugin.h"
#if __has_include(<payumoney_plugin/payumoney_plugin-Swift.h>)
#import <payumoney_plugin/payumoney_plugin-Swift.h>

#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "payumoney_plugin-Swift.h"
#endif

@implementation PayumoneyPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftPayumoneyPlugin registerWithRegistrar:registrar];
}
@end
