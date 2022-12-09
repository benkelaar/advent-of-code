plugins {
    kotlin("jvm") version "1.7.20"
    application
}

group = "com.bartenkelaar"
version = "2022.1"
val targetJvmVersion = "18"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.code.gson:gson:2.8.9")

    testImplementation(kotlin("test-junit5"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

application {
    mainClass.set("com.bartenkelaar.MainKt")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = targetJvmVersion
        }
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = targetJvmVersion
        }
    }

    test {
        useJUnitPlatform()
    }

    jar {
        manifest {
            attributes("Main-Class" to "com.bartenkelaar.MainKt")
        }
    }
}