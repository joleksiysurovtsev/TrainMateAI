// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.10.1" apply false
    id("org.jetbrains.kotlin.android") version "2.1.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.21" apply false
}

buildscript {
    repositories{
        google()
        mavenCentral()
    }
    dependencies { classpath("com.squareup:javapoet:1.13.0") }

    configurations["classpath"].resolutionStrategy {
        force("com.squareup:javapoet:1.13.0")
    }
}