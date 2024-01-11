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
    //mockito
    testImplementation ("org.mockito:mockito-core:3.11.2")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    //for email validation
    implementation ("org.springframework.boot:spring-boot-starter-mail")
    implementation ("org.springframework:spring-context-support:5.3.10")
    implementation ("javax.mail:javax.mail-api:1.5.6")
    implementation ("javax.mail:mail:1.4.7")
    implementation ("io.github.cdimascio:java-dotenv:5.2.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
