import ru.rutoken.runCommand

plugins {
    `java-library`
    `maven-publish`
    signing
}

version = AppConfig.versionName

tasks.jar {
    archiveBaseName.set("pkcs11wrapper")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    compileOnly(Dependencies.jetbrainsAnnotations)
    api(Dependencies.pkcs11jna)
}

val isProduction = project.findProperty("production") == "true"

publishing {
    val jarVersion = if (isProduction) {
        "${project.version}"
    } else {
        val commitHash = runCommand("git rev-parse --verify --short HEAD")
        "${project.version}-$commitHash"
    }

    publications {
        create<MavenPublication>("pkcs11wrapper") {
            groupId = "ru.rutoken.pkcs11wrapper"
            artifactId = "pkcs11wrapper"
            version = jarVersion
            from(components["java"])
            pom {
                name.set("$groupId:$artifactId")
                description.set("A Java library that simplifies PKCS11 native (C) library usage from Java code")
                url.set("https://github.com/AktivCo/pkcs11wrapper") // todo: check it before publishing on github
                packaging = "jar"
                licenses {
                    license {
                        name.set("Aktiv-Soft license")
                        url.set("https://github.com/AktivCo/pkcs11wrapper/blob/master/LICENSE")
                    }
                }
                developers {
                    developer {
                        name.set("Aktiv-Soft JSC")
                        email.set("developers@rutoken.ru")
                        organization.set("Aktiv-Soft JSC")
                        organizationUrl.set("https://www.aktiv-company.ru/")
                    }
                }
                scm {
                    url.set("https://github.com/AktivCo/pkcs11wrapper/tree/master")
                }
            }
        }
    }
    repositories {
        maven {
            name = "Maven"
            url = if (isProduction) {
                // Maven Central
                uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            } else {
                // Internal Aktiv repository
                uri("https://nexus.aktivco.ru/repository/maven-snapshots/")
            }
            credentials {
                username = project.findProperty("mavenUser")?.toString()
                password = project.findProperty("mavenPassword")?.toString()
            }
        }
    }
}

signing {
    if (isProduction) {
        sign(publishing.publications["pkcs11wrapper"])
    }
}