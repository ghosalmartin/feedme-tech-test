import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.1.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm")
	kotlin("plugin.spring") version "1.3.50"
}

dependencies {
	implementation(project(":models"))

	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.springframework.kafka:spring-kafka")

	implementation("org.apache.kafka:kafka-streams")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

	testImplementation("org.springframework.amqp:spring-rabbit-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
    implementation(kotlin("stdlib-jdk8"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}