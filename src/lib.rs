use tauri::{
    plugin::{Builder, TauriPlugin},
    Manager, Runtime,
};

#[cfg(desktop)]
mod desktop;
#[cfg(mobile)]
mod mobile;

mod error;

pub use error::{Error, Result};

#[cfg(desktop)]
use desktop::Admob;
#[cfg(mobile)]
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
            #[cfg(mobile)]
            let admob = mobile::init(app, api)?;
            #[cfg(desktop)]
            let admob = desktop::init(app, api)?;
            app.manage(admob);
            Ok(())
        })
        .build()
}
