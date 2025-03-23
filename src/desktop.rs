use serde::de::DeserializeOwned;
use tauri::{plugin::PluginApi, AppHandle, Runtime};

pub fn init<R: Runtime, C: DeserializeOwned>(
    app: &AppHandle<R>,
    _api: PluginApi<R, C>,
) -> crate::Result<Admob<R>> {
    Ok(Admob(app.clone()))
}

/// Access to the Admob APIs.
pub struct Admob<R: Runtime>(AppHandle<R>);
