const COMMANDS: &[&str] = &["trackingAuthorizationStatus", "requestTrackingAuthorization", "configure", "configRequest", "adCreate", "adIsLoaded", "adLoad", "adShow", "adHide", "isPrivacyOptionsRequired", "showPrivacyOptionsForm", "register_listener"];

fn main() {
  tauri_plugin::Builder::new(COMMANDS)
    .android_path("android")
    .ios_path("ios")
    .build();
}
