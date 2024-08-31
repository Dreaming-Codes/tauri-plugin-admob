{ pkgs }:

with pkgs;

# Configure your development environment.
#
# Documentation: https://github.com/numtide/devshell
devshell.mkShell {
  name = "android-project";
  motd = ''
    Entered the Android app development environment.
  '';
  env = [
    {
      name = "ANDROID_HOME";
      value = "${android-sdk}/share/android-sdk";
    }
    {
      name = "ANDROID_SDK_ROOT";
      value = "${android-sdk}/share/android-sdk";
    }
    {
      name = "NDK_HOME";
      value = "${android-sdk}/share/android-sdk/ndk/26.1.10909125";
    }
    {
      name = "JAVA_HOME";
      value = jdk17.home;
    }
    {
      name = "PKG_CONFIG_PATH";
      value = "${pkgs.openssl.dev}/lib/pkgconfig";
    }
  ];
  packages = [
    android-sdk
    gradle
    jdk17
    webkitgtk_4_1
    file
    openssl
    libiconv
    pkg-config
    libappindicator-gtk3
    librsvg
    rustup
    cargo-make
    gcc
    cargo-watch
    sqlx-cli
    bun
    nodejs
    jq
  ];

  devshell.startup.initRust.text = ''
    rustup default stable
    rustup target add aarch64-linux-android armv7-linux-androideabi i686-linux-android x86_64-linux-android
  '';
}
