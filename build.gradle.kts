
plugins {
    base
    kotlin("jvm") version "1.3.50" apply false
}

allprojects {

    group = "com.cba.feedmetechtest"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}