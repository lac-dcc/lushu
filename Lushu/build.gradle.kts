import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

repositories {
    jcenter()
}

plugins {
    kotlin("jvm") version "1.8.22"

    // Apply the application plugin to add support for building a CLI
    // application.
    application

    // This pluging allows greater flexibility in customizing test builds.
    id("org.unbroken-dome.test-sets") version "4.0.0"
}

dependencies {
    implementation("org.slf4j:slf4j-log4j12:2.0.3")

    // Kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    // Jackson -- to parse YAML files
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.2")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
}

val fatJar = task("fatJar", type = Jar::class) {
    description = "Creates a self-contained fat JAR of the application."
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to "Lushu",
                "Implementation-Version" to "0.1",
                "Main-Class" to "lushu.AppKt"
            )
        )
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree)
    from(dependencies)
    with(tasks.jar.get())
}

val HTMLJar = task("mapGrammarJar", type = Jar::class) {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to "Lushu",
                "Implementation-Version" to "0.1",
                "Main-Class" to "lushu.MapAppKt"
            )
        )
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree)
    from(dependencies)
    with(tasks.jar.get())
}

val stressTestMapWithLushuJar =
    task("stressTestMapWithLushuJar", type = Jar::class) {
        archiveBaseName.set("stressTestMapWithLushu")
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to "stressTestMapWithLushu",
                    "Implementation-Version" to "0.1",
                    "Main-Class" to "lushu.TestApps.StressTest.Context.WithLushu.AppKt"
                )
            )
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        val dependencies = configurations
            .runtimeClasspath
            .get()
            .map(::zipTree)
        from(dependencies)
        with(tasks.jar.get())
    }

val grammarJar = task("grammarJar", type = Jar::class) {
    archiveBaseName.set("Grammar")
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to "Grammar",
                "Implementation-Version" to "0.1",
                "Main-Class" to "lushu.Grammar.AppKt"
            )
        )
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree)
    from(dependencies)
    with(tasks.jar.get())
}

val mergerJar = task("mergerJar", type = Jar::class) {
    archiveBaseName.set("Merger")
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to "Merger",
                "Implementation-Version" to "0.1",
                "Main-Class" to "lushu.Merger.AppKt"
            )
        )
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree)
    from(dependencies)
    with(tasks.jar.get())
}

val stressTestWithLushuJar =
    task("stressTestWithLushuJar", type = Jar::class) {
        archiveBaseName.set("StressTestWithLushu")
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to "StressTestWithLushu",
                    "Implementation-Version" to "0.1",
                    "Main-Class" to "lushu.TestApps.StressTest.WithLushu.AppKt"
                )
            )
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        val dependencies = configurations
            .runtimeClasspath
            .get()
            .map(::zipTree)
        from(dependencies)
        with(tasks.jar.get())
    }

val stressTestWoutLushuJar =
    task("stressTestWoutLushuJar", type = Jar::class) {
        archiveBaseName.set("StressTestWoutLushu")
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to "StressTestWoutLushu",
                    "Implementation-Version" to "0.1",
                    "Main-Class" to "lushu.TestApps.StressTest.WoutLushu.AppKt"
                )
            )
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        val dependencies = configurations
            .runtimeClasspath
            .get()
            .map(::zipTree)
        from(dependencies)
        with(tasks.jar.get())
    }

val stressTestGrammarStatisticsJar =
    task("stressTestGrammarStatisticsJar", type = Jar::class) {
        archiveBaseName.set("StressTestGrammarStatistics")
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to "StressTestGrammarStatistics",
                    "Implementation-Version" to "0.1",
                    "Main-Class" to "lushu.TestApps.StressTest.GrammarStatistics.AppKt"
                )
            )
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        val dependencies = configurations
            .runtimeClasspath
            .get()
            .map(::zipTree)
        from(dependencies)
        with(tasks.jar.get())
    }

tasks {
    "build" {
        dependsOn(fatJar)
    }
    "distTar" {
        dependsOn(fatJar)
    }
    "distZip" {
        dependsOn(fatJar)
    }
    "startScripts" {
        dependsOn(fatJar)
    }
}

application {
    // Default application name
    mainClass.set("lushu.Grammar.AppKt")
}

tasks.test {
    testLogging.showStandardStreams = true

    useJUnitPlatform()

    testLogging {
        lifecycle {
            events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
            exceptionFormat = TestExceptionFormat.FULL
            showExceptions = true
            showCauses = true
            showStackTraces = true
            showStandardStreams = true
        }
        info.events = lifecycle.events
        info.exceptionFormat = lifecycle.exceptionFormat
    }

    val failedTests = mutableListOf<TestDescriptor>()
    val skippedTests = mutableListOf<TestDescriptor>()

    // See https://github.com/gradle/kotlin-dsl/issues/836
    addTestListener(object : TestListener {
        override fun beforeSuite(suite: TestDescriptor) {}
        override fun beforeTest(testDescriptor: TestDescriptor) {}
        override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
            when (result.resultType) {
                TestResult.ResultType.FAILURE -> failedTests.add(testDescriptor)
                TestResult.ResultType.SKIPPED -> skippedTests.add(testDescriptor)
                else -> Unit
            }
        }

        override fun afterSuite(suite: TestDescriptor, result: TestResult) {
            if (suite.parent == null) { // root suite
                logger.lifecycle("----")
                logger.lifecycle("Test result: ${result.resultType}")
                logger.lifecycle(
                    "Test summary: ${result.testCount} tests, " +
                            "${result.successfulTestCount} succeeded, " +
                            "${result.failedTestCount} failed, " +
                            "${result.skippedTestCount} skipped"
                )
                failedTests.takeIf { it.isNotEmpty() }?.prefixedSummary("\tFailed Tests")
                skippedTests.takeIf { it.isNotEmpty() }?.prefixedSummary("\tSkipped Tests:")
            }
        }

        private infix fun List<TestDescriptor>.prefixedSummary(subject: String) {
            logger.lifecycle(subject)
            forEach { test -> logger.lifecycle("\t\t${test.displayName()}") }
        }

        private fun TestDescriptor.displayName() = parent?.let { "${it.name} - $name" } ?: "$name"
    })
}
