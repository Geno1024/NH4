import com.android.build.gradle.tasks.PackageAndroidArtifact
import g.buildsrc.BuildCount

plugins {
    id("com.android.application")
    kotlin("android")
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

android {
    compileSdk = 36
    namespace = "g.sw.planet"
}
