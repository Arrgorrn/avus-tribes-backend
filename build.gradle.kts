plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.gfa"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    runtimeOnly("com.mysql:mysql-connector-j")
//    implementation 'org.springframework.boot:spring-boot-starter-security'
//    implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
//    implementation 'io.jsonwebtoken:jjwt-impl:0.11.5'
//    implementation 'io.jsonwebtoken:jjwt-jackson:0.11.5'
//    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
//    runtimeOnly 'com.h2database:h2'
//    testImplementation 'org.springframework.security:spring-security-test'
}

tasks.withType<Test> {
    useJUnitPlatform()
}
