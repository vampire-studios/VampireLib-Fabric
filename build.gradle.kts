import net.fabricmc.loom.task.RemapJarTask
import net.fabricmc.loom.task.RemapSourcesJarTask

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

version = "${Constants.version}+${Constants.minecraftVersionVer}"
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

	modCompile(group = "net.fabricmc", name = "fabric", version = Fabric.API.version + ".+")
	include(group = "net.fabricmc", name = "fabric", version = Fabric.API.version + ".+")

	modCompile(group = "com.github.siphalor", name = "tweed-api", version = Dependencies.Tweed.version)
	include(group = "com.github.siphalor", name = "tweed-api", version = Dependencies.Tweed.version)

	modCompile(group = "io.github.cottonmc", name = "cotton", version = "0.6.1+1.14-SNAPSHOT")

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

val remapJar = tasks.getByName<RemapJarTask>("remapJar")

val remapSourcesJar = tasks.getByName<RemapSourcesJarTask>("remapSourcesJar")

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
			artifactId = "AbnormaLib"
            artifact(jar) {
				builtBy(remapJar)
			}
			artifact(sourcesJar.get()) {
				builtBy(remapSourcesJar)
			}
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