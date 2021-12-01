plugins {
    kotlin("jvm") version "1.6.0"
}

group = "com.bartenkelaar"
version = "2021.1"
val targetJvmVersion = "17"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test-junit5"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
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