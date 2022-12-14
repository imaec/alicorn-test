plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdk = Apps.compileSdk
    buildToolsVersion = Apps.build_tools
    defaultConfig {
        minSdk = Apps.minSdk
        targetSdk = Apps.targetSdk
        versionCode = Apps.versionCode
        versionName = Apps.versionName

        multiDexEnabled = true
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

kapt {
    useBuildCache = true
}

dependencies {
    coreLibraryDesugaring(Libs.desugar)

    implementation(project(":domain"))
    implementation(project(":data"))

    testImplementation(TestLibs.junit)
    androidTestImplementation(TestLibs.junit_ext)
    androidTestImplementation(TestLibs.espresso)

    implementation(Libs.kotlin)
    implementation(Libs.appcompat)
    implementation(Libs.core_ktx)
    implementation(Libs.material)
    implementation(Libs.constraint_layout)
    implementation(Libs.activity_ktx)

    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.lifecycle_livedata_ktx)
    implementation(Libs.lifecycle_extensions)

    implementation(Libs.coroutine_core)
    implementation(Libs.coroutine_android)

    implementation(Libs.dagger_hilt_android)
    kapt(Libs.dagger_hilt_compiler)
    implementation(Libs.hilt_common)
    kapt(Libs.hilt_compiler)

    implementation(Libs.retrofit)
    implementation(Libs.retrofit_converter)
    implementation(Libs.okhttp3)
    implementation(Libs.okhttp3_interceptor)

    implementation(Libs.stomp)

    implementation(Libs.timber)
}

ktlint {
    debug.set(true)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}
