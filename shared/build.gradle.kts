import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.multiplatform.library)
}

kotlin {
    android {
        namespace = "com.example.giftpackinghelper"
        compileSdk = 37
        minSdk = 27
        compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
        withHostTestBuilder {}.configure {}
    }

    val xcframework = XCFramework("Shared")
    listOf(iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "Shared"
            isStatic = true
            binaryOption("bundleId", "com.example.giftpackinghelper.shared")
            xcframework.add(this)
        }
    }

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

// SPM resolves binary targets before Xcode build phases. Prepare the package
// before opening Xcode, or use scripts/build-ios.sh.
tasks.register<Sync>("exportSwiftPackage") {
    group = "distribution"
    description = "Build the device/simulator XCFramework and prepare the local Swift package."
    dependsOn("assembleSharedReleaseXCFramework")
    from(layout.buildDirectory.dir("XCFrameworks/release/Shared.xcframework"))
    into(layout.projectDirectory.dir("../SharedPackage/Shared.xcframework"))
}
