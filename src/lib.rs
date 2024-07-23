#![cfg(mobile)]

use tauri::{
  plugin::{Builder, TauriPlugin},
  Manager, Runtime,
};

mod mobile;

mod error;

pub use error::{Error, Result};

use mobile::Admob;

/// Extensions to [`tauri::App`], [`tauri::AppHandle`] and [`tauri::Window`] to access the admob APIs.
pub trait AdmobExt<R: Runtime> {
  fn admob(&self) -> &Admob<R>;
}

impl<R: Runtime, T: Manager<R>> crate::AdmobExt<R> for T {
  fn admob(&self) -> &Admob<R> {
    self.state::<Admob<R>>().inner()
  }
}

/// Initializes the plugin.
pub fn init<R: Runtime>() -> TauriPlugin<R> {
  Builder::new("admob")
    .setup(|app, api| {
      let admob = mobile::init(app, api)?;
      app.manage(admob);
      Ok(())
    })
    .build()
}
