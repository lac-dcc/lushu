import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

repositories {
    jcenter()
}

plugins {
    kotlin("jvm") version "1.8.22"

    // The application plugin enables building a CLI application.
    application

    // This plugin allows greater flexibility in customizing test builds.
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

fun newJarTask(
    name: String,
    desc: String,
    packagePath: String,
    manifestAttributes: Map<String, String> = mapOf(
        "Implementation-Title" to "Lushu",
        "Implementation-Version" to "0.1",
        "Main-Class" to packagePath
    )
): Task {
    return task(name, type = Jar::class) {
        description = desc
        manifest {
            attributes(manifestAttributes)
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        val dependencies = configurations
            .runtimeClasspath
            .get()
            .map(::zipTree)
        from(dependencies)
        with(tasks.jar.get())
    }
}

val fatJar = newJarTask(
    "fatJar",
    "Creates a self-contained fat JAR of the application.",
    "lushu.Appkt"
)

newJarTask(
    "grammarJar",
    "Application to play with Lushu Grammar.",
    "lushu.Grammar.AppKt"
)

newJarTask(
    "mergerJar",
    "Application to play with Lushu Merger.",
    "lushu.Merger.AppKt"
)

newJarTask(
    "generatorJar",
    "Application to generate test files",
    "lushu.ContextGrammar.GeneratorAppKt"
)

newJarTask(
    "stressTestMapWithLushuJar",
    "Application to test Lushu Map Grammar implementation.",
    "lushu.TestApps.StressTest.Context.WithLushu.AppKt"
)

newJarTask(
    "stressTestWithLushuJar",
    "Application to test stable Lushu implementation.",
    "lushu.TestApps.StressTest.WithLushu.AppKt"
)

newJarTask(
    "stressTestWoutLushuJar",
    "Application to test base case (program running without Lushu interception) against Lushu.",
    "lushu.TestApps.StressTest.WoutLushu.AppKt"
)

newJarTask(
    "stressTestGrammarStatisticsJar",
    "Application to get some statistics about the Lushu Grammar after running against some benchmark.",
    "lushu.TestApps.StressTest.GrammarStatistics.AppKt"
)

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
