plugins {
    kotlin("jvm") version "2.1.0"
    id("me.champeau.jmh") version "0.7.2"
}

group = "org.wrongwrong"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        setUrl("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

val isAfter = true

dependencies {
    jmhImplementation(kotlin("reflect"))
    jmhImplementation("com.fasterxml.jackson.core:jackson-databind:2.19.0-SNAPSHOT")

    if (isAfter) {
        jmhImplementation(files("./jars/jackson-module-kotlin-2.19.0-aa167f.jar"))
    } else {
        jmhImplementation(files("./jars/jackson-module-kotlin-2.19.0-4ecdac.jar"))
    }

    testImplementation(kotlin("test"))
}

val generatedSrcPath = "${layout.buildDirectory.get()}/generated/kotlin"

tasks.test {
    useJUnitPlatform()
}
kotlin {
    // for withCheckMapper
    sourceSets["jmh"].apply {
        kotlin.srcDir(generatedSrcPath)
    }
    jvmToolchain(8)
}

tasks {
    val generateWithCheckMapper by registering(Copy::class) {
        val packageStr = "org.wrongwrong"
        val strictNullChecksStr = if (isAfter) {
            "NewStrictNullChecks"
        } else {
            "StrictNullChecks"
        }

        from(
            resources.text.fromString(
                """
package $packageStr

import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

val withCheckMapper = jacksonObjectMapper { enable(KotlinFeature.${strictNullChecksStr}) }

                """.trimIndent()
            )
        ) {
            rename { "generatedCommon.kt" }
        }

        into(file("$generatedSrcPath/${packageStr.replace(".", "/")}"))
    }

    compileKotlin {
        dependsOn.add(generateWithCheckMapper)
    }
}

jmh {
    warmupForks = 2
    warmupBatchSize = 3
    warmupIterations = 3
    warmup = "1s"

    fork = 2
    batchSize = 3
    iterations = 2
    timeOnIteration = "1500ms"

    failOnError = true

    resultFormat = "CSV"
}
