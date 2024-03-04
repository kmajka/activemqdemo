plugins {
	java
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.jms.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-activemq")
	implementation("org.apache.activemq:activemq-broker")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.apache.activemq:activemq-kahadb-store")
	implementation("javax.jms:javax.jms-api:2.0.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
