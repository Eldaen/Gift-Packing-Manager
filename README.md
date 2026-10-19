# Gift Packing Manager

Калькулятор упаковочной бумаги: общая логика Kotlin Multiplatform, SwiftUI на iOS и Jetpack Compose Material 3 на Android. Размеры подарка и бумаги указаны в сантиметрах. Обе платформы принимают десятичную запятую и точку.

## Инструменты

- JDK 17, Kotlin 2.4.21, Gradle 9.5.0 (wrapper), AGP 9.3.1.
- Android compile SDK 37 / target SDK 36, minSdk 27; SDK задаётся через `ANDROID_HOME` или `sdk.dir` в локальном `local.properties`.
- Xcode 26.4 на Apple Silicon, iOS 16+. Для проверки использован iPhone 17 Pro Simulator / iOS 26.4.
- XcodeGen требуется только при изменении `iosApp/project.yml`; готовый Xcode-проект и схема включены в репозиторий.

## iOS и Swift Package Manager

После клонирования **сначала соберите локальный бинарный SPM-пакет**, затем открывайте Xcode:

```sh
./gradlew :shared:exportSwiftPackage
open iosApp/iosApp.xcodeproj
```

Или соберите приложение одной командой (SPM-пакет будет обновлён автоматически):

```sh
./scripts/build-ios.sh CODE_SIGNING_ALLOWED=NO
```

По умолчанию скрипт выбирает iPhone 17 Pro Simulator. Другую destination можно передать через `IOS_DESTINATION`. Для физического iPhone выберите Development Team в Xcode и доступное устройство; подпись симуляторной сборке не нужна.

`SharedPackage/Package.swift` экспортирует продукт `Shared` через `binaryTarget`. Gradle собирает статический `Shared.xcframework` с `ios-arm64` и `ios-arm64-simulator`. Swift использует `import Shared`. CocoaPods не нужен. В XCFramework поставляется Objective-C/Swift interop API Kotlin, а не экспериментальный Swift export.

Бинарник исключён из Git. Это локальный SPM-пакет монорепозитория: SPM не запускает Gradle самостоятельно и проверяет наличие бинарника ещё до build phases. **После изменений Kotlin повторите экспорт** или пользуйтесь `build-ios.sh`. Для распространения в другом репозитории XCFramework можно упаковать в ZIP и заменить локальный `path` в манифесте на URL и checksum по [официальной инструкции Kotlin](https://kotlinlang.org/docs/multiplatform/multiplatform-spm-export.html).

Изменение Xcode-проекта:

```sh
xcodegen generate --spec iosApp/project.yml
```

## Android

```sh
./gradlew :androidApp:assembleDebug :androidApp:assembleRelease :androidApp:lintDebug
```

APK: `androidApp/build/outputs/apk/debug/androidApp-debug.apk`; release APK не подписан. Интерфейс повторяет iOS: иллюстрация, три поля, красные кнопки, карточка результата, светлая и тёмная темы. Системная кнопка Back возвращает к введённым размерам; «Новый подарок» очищает поля.

## Проверки

```sh
# Формула и ввод на JVM и Kotlin/Native
./gradlew :shared:testAndroidHostTest :shared:iosSimulatorArm64Test

# Android UI: расчёт/сброс и рендеры на API 35 в JVM, без Android-эмулятора
./gradlew :androidApp:testDebugUnitTest

# После экспорта SPM-пакета: UI-сценарий на iPhone 17 Pro Simulator
xcodebuild -project iosApp/iosApp.xcodeproj -scheme iosApp \
  -destination 'platform=iOS Simulator,name=iPhone 17 Pro' \
  -derivedDataPath build/ios -resultBundlePath build/ios-tests.xcresult \
  CODE_SIGNING_ALLOWED=NO test
```

При повторном запуске Xcode-тестов укажите новый `-resultBundlePath`, если предыдущий xcresult уже существует.

Android-рендеры сохраняются в `androidApp/build/ui-check/`, HTML-отчёт — в `androidApp/build/reports/tests/testDebugUnitTest/`. iOS-скриншоты прикреплены к xcresult. Состояние миграции, решения и результаты проверок записаны в `PLAN.md`.
