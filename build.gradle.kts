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
group = "team.abnormals"

repositories {
	mavenCentral()
	maven("https://maven.fabricmc.net")
	maven("https://minecraft.curseforge.com/api/maven")
	maven("https://maven.jamieswhiteshirt.com/libs-release/")
	maven("http://server.bbkr.space:8081/artifactory/libs-snapshot")
	maven("http://maven.sargunv.s3-website-us-west-2.amazonaws.com/")
}

dependencies {
	minecraft(group = "com.mojang", name = "minecraft", version = Minecraft.version)
	mappings(group = "net.fabricmc", name = "yarn", version = "${Minecraft.version}+build.${Fabric.Yarn.version}")

	modCompile(group = "net.fabricmc", name = "fabric-loader", version = Fabric.Loader.version)

	modCompile(group = "net.fabricmc.fabric-api", name = "fabric-api", version = Fabric.API.version)

	modCompile(group = "io.github.prospector.modmenu", name = "ModMenu", version = Dependencies.ModMenu.version)
}

val processResources = tasks.getByName<ProcessResources>("processResources") {
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        filter { line -> line.replace("%VERSION%", "${project.version}") }
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
		afterEvaluate {
			register("mavenJava", MavenPublication::class) {
				artifactId = Constants.name
				artifact(jar)
				artifact(sourcesJar.get()) {
					builtBy(remapSourcesJar)
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
}