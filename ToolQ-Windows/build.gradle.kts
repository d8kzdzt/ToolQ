plugins {
    kotlin("jvm") version "1.4.30"
    id("com.github.johnrengelman.shadow") version "6.1.0" // 全量打包插件
}

group = "com.toolq"
version = "1.0-SNAPSHOT"

dependencies {
    val ktorVersion = "1.5.1"

    implementation(project(":ToolQ-Core"))

    implementation("com.google.code.gson", "gson", "2.8.6")
    implementation("com.squareup.okhttp3", "okhttp", "4.9.0")

    implementation(
        fileTree(
            mapOf(
                "dir" to "libs",
                "include" to listOf("*.jar")
            )
        )
    )
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to "test.Main"
            )
        )
    }
}

tasks.build {
    // build时执行shadowJar任务
    dependsOn(tasks.shadowJar)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
