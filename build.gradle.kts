plugins {
    id("java")
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass = "analyzer.Main"
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.github.javaparser:javaparser-core:3.26.0")
    implementation("com.github.javaparser:javaparser-symbol-solver-core:3.26.0")

}

tasks.test {
    useJUnitPlatform()
}