use serde::de::DeserializeOwned;
use tauri::{
  plugin::{PluginApi, PluginHandle},
  AppHandle, Runtime,
};

#[cfg(target_os = "android")]
const PLUGIN_IDENTIFIER: &str = "com.plugin.admob";

#[cfg(target_os = "ios")]
tauri::ios_plugin_binding!(init_plugin_admob);

// initializes the Kotlin or Swift plugin classes
pub fn init<R: Runtime, C: DeserializeOwned>(
  _app: &AppHandle<R>,
  api: PluginApi<R, C>,
) -> crate::Result<Admob<R>> {
  #[cfg(target_os = "android")]
  let handle = api.register_android_plugin(PLUGIN_IDENTIFIER, "AdmobPlugin")?;
  #[cfg(target_os = "ios")]
  let handle = api.register_ios_plugin(init_plugin_admob)?;
  Ok(Admob(handle))
}

/// Access to the admob APIs.
pub struct Admob<R: Runtime>(PluginHandle<R>);

impl<R: Runtime> Admob<R> {}
