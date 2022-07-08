plugins {
    id("project.publishing-conventions")
}

repositories {
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    compileOnly(libs.spigot)
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.3")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.5")
}