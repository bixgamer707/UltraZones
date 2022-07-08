plugins {
    id("project.common-conventions")
    alias(libs.plugins.shadow)
}

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    api(project(":api"))
    compileOnly(libs.spigot)
    compileOnly(libs.placeholder)
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.6")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.8")
}

tasks {
    shadowJar {
        archiveBaseName.set("UltraZones")
        destinationDirectory.set(file("$rootDir/bin/"))
        minimize()
    }

    clean {
        delete("${rootDir}/bin/")
    }
}