# Tauri Plugin admob

> For now this is just a copy of [admob-plus](https://github.com/admob-plus/admob-plus) in the future I would like to refactor the code to be more Tauri friendly.

## How to install

Since this plugin is yet not published to crates.io or npm registery, we can install it using github link directly.

1. Inside your root directory run this command

```bash
$ npm i https://github.com/Dreaming-Codes/tauri-plugin-admob.git
```

2. Inside your `src-tauri` directory run this command

```bash
$ cargo add tauri-plugin-admob --git https://github.com/Dreaming-Codes/tauri-plugin-admob.git
```

3. Since google library uses different Kotlin version than Tauri you may need to add the following compiler
   arg: `-Xskip-metadata-version-check`

```kotlin
// src-tauri/gen/android/app/build.gradle.kts
kotlinOptions {
    // ...
    freeCompilerArgs += "-Xskip-metadata-version-check"
}
```

4. Add your AdMob app ID, as [identified in the AdMob web interface](https://support.google.com/admob/answer/7356431),
   to your app's AndroidManifest.xml.
   To do so, add a `<meta-data>` tag with `android:name="com.google.android.gms.ads.APPLICATION_ID"`. You can find your
   **app ID** in the AdMob web interface. For `android:value`, insert your own AdMob app ID, surrounded by quotation
   marks.

```xml
<!-- ./src-tauri/gen/android/app/src/main/AndroidManifest.xml -->
<manifest>
    <application>
        <!-- Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713 -->
        <meta-data
                android:name="com.google.android.gms.ads.APPLICATION_ID"
                android:value="ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy"/>
        <meta-data
                android:name="com.google.android.gms.ads.DELAY_APP_MEASUREMENT_INIT"
                android:value="true"/>
    </application>
</manifest>
```

## How to use

```ts
import { BannerAd } from "tauri-plugin-admob-api";

const showBanner = async () => {
  const banner = new BannerAd({
    adUnitId: "ca-app-pub-3940256099942544/9214589741",
    position: "bottom",
  });
  if (!(await banner.isLoaded())) {
    await banner.load();
  }
  await banner.show();
};
```

> For more details on how to use it check the documentation of [admob-plus](https://github.com/admob-plus/admob-plus)
