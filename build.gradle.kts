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

version = "${Constants.version}+${Constants.minecraftVersionVer}-${Constants.ending}"
group = "team.abnormals"

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
			artifactId = Constants.name
			afterEvaluate {
    			artifact(remapJar.output)
			}
			pom {
				name.set(Constants.name)
				description.set(Constants.description)
				url.set(Constants.url)
				licenses {
					license {
						name.set("MIT License")
						url.set("https://tldrlegal.com/license/mit-license#fulltext")
					}
				}
				developers {
					developer {
						id.set("team_abnormals")
						name.set("Team Abnormals")
					}
				}
			}
        }
    }
}