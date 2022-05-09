plugins {
    `java-library`
}

repositories {
    mavenLocal()
    mavenCentral()
}

tasks {
    java {
        toolchain {
            languageVersion.set(
                JavaLanguageVersion.of("${findProperty("java")}")
            )
        }
    }

    compileJava {
        options.compilerArgs.add("-parameters")
    }
}