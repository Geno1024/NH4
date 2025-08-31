import g.buildsrc.BuildCount
import org.gradle.kotlin.dsl.withType

plugins {
    kotlin("jvm")
}

dependencies {
    implementation("org.jetbrains.kotlin", "kotlin-reflect", kotlin.coreLibrariesVersion)
}

// <editor-fold desc="Build Count">
val run = BuildCount(project, "run")

val runCount = tasks.register("runCount") {
    group = "buildCount"
    doLast {
        run.inc()
    }
}

val jarCount = BuildCount(project, "jar")

val jar = tasks.register("jarCount") {
    group = "buildCount"
    doLast {
        jarCount.inc()
    }
}

tasks.withType<JavaCompile> {
    dependsOn(runCount)
}

tasks.withType<Jar> {
    dependsOn(jarCount)
}
// </editor-fold>
