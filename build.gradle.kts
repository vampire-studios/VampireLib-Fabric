import net.fabricmc.loom.task.RemapJar
import net.fabricmc.loom.task.RemapSourcesJar

plugins {
	wrapper
	idea
	id("fabric-loom") version Fabric.Loom.version
	id("maven-publish")
	kotlin("jvm") version "1.3.21"
}

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

idea {
	module {
		excludeDirs.add(file("run"))
	}
}

base {
	archivesBaseName = Constants.name
}

version = "${Constants.version}+${Constants.minecraftVersionVer}-${Constants.ending}"
group = "team.hollow"

repositories {
	mavenCentral()
	maven("https://jitpack.io")
	maven("https://tehnut.info/maven")
	maven("https://maven.fabricmc.net")
	maven("https://minecraft.curseforge.com/api/maven")
	maven("https://maven.jamieswhiteshirt.com/libs-release/")
	maven("http://server.bbkr.space:8081/artifactory/libs-snapshot")
}

dependencies {
	minecraft(group = "com.mojang", name = "minecraft", version = Minecraft.version)
	mappings(group = "net.fabricmc", name = "yarn", version = "${Minecraft.version}+build.${Fabric.Yarn.version}")

	modCompile(group = "net.fabricmc", name = "fabric-loader", version = Fabric.Loader.version)

	modCompile(group = "net.fabricmc.fabric-api", name = "fabric-api", version = Fabric.API.version)

	modCompile(group = "io.github.cottonmc", name = "cotton", version = "0.6.7+1.14.1-SNAPSHOT")

}

tasks.getByName<ProcessResources>("processResources") {
	filesMatching("fabric.mod.json") {
		expand(
				mutableMapOf(
						"version" to version
				)
		)
	}
}

val javaCompile = tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

val sourcesJar by tasks.registering(Jar::class) {
    from(sourceSets["main"].allSource)
    classifier = "sources"
}

val jar = tasks.getByName<Jar>("jar") {
    from("LICENSE")
}

val remapJar = tasks.getByName<RemapJar>("remapJar")

val remapSourcesJar = tasks.getByName<RemapSourcesJar>("remapSourcesJar")

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
			artifactId = "AbnormaLib"
			artifact(tasks["jar"])
			artifact(tasks["sourcesJar"])
            pom {
                name.set("AbnormaLib")
                description.set(Constants.description)
                url.set(Constants.url)
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("minecraft_abnormals")
                        name.set("Minecraft Abnormals")
                    }
                }
            }
        }
    }
}