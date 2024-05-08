import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"

    id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
    id("org.openapi.generator") version "7.4.0"
}



group = "com.presignedurl"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("openApiGeneratorAll"){
    dependsOn(
        "openApiGeneratorForSwift",
        "openApiGeneratorForTypescript",
    )
}


tasks.register<GenerateTask>("openApiGeneratorForSwift") {
    generatorName = "swift5"
    inputSpec = project.layout.buildDirectory.dir("openapi.json").get().asFile.path
    outputDir = project.layout.buildDirectory.dir("openapi/swift/OpenApIClient").get().asFile.path
}


tasks.register<GenerateTask>("openApiGeneratorForTypescript") {
    generatorName = "typescript-axios"
    inputSpec = project.layout.buildDirectory.dir("openapi.json").get().asFile.path
    outputDir = project.layout.buildDirectory.dir("openapi/typescript/OpenApIClient").get().asFile.path
}

//tasks.register("convertJsonToYaml") {
//    doLast {
//        val jsonFile = file("src/main/resources/static/docs/api-docs.json")
//        val yamlFile = file("src/main/resources/static/docs/api-docs.yaml")
//        "json2yaml $jsonFile > $yamlFile"
//    }
//}
