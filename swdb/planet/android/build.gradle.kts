import com.android.build.gradle.tasks.PackageAndroidArtifact
import g.buildsrc.BuildCount

plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation("androidx.drawerlayout", "drawerlayout", "1.2.0")
    implementation("androidx.navigation", "navigation-ui-ktx", "2.9.3")
}

// <editor-fold desc="Build Count">
val run = BuildCount(project, "run")

val runCount = tasks.register("runCount") {
    group = "buildCount"
    doLast {
        run.inc()
    }
}

val android = BuildCount(project, "android")

val androidCount = tasks.register("androidCount") {
    group = "buildCount"
    doLast {
        android.inc()
    }
}

tasks.withType<JavaCompile> {
    dependsOn(runCount)
}

tasks.withType<PackageAndroidArtifact> {
    dependsOn(androidCount)
}
// </editor-fold>

kotlin {
    jvmToolchain(21)
}

android {
    compileSdk = 36
    namespace = "g.sw.planet"
    defaultConfig {
        applicationId = "g.sw.planet"
        minSdk = 21
        targetSdk = 36
        versionCode = android.read() + 1
        versionName = "0.0.1.$versionCode"
    }
}
