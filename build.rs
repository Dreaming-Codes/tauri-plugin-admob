const COMMANDS: &[&str] = &["trackingAuthorizationStatus", "requestTrackingAuthorization", "configure", "configRequest", "adCreate", "adIsLoaded", "adLoad", "adShow", "adHide"];

fn main() {
  tauri_plugin::Builder::new(COMMANDS)
    .android_path("android")
    .ios_path("ios")
    .build();
}
