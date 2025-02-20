// app/build.gradle.kts
/*
 * This file was generated by the Gradle 'init' task.
 */

// testestestestet for testing!

application { // entry-points
    // Define the main class for the application.
    // mainClass.set("eugene.app.Cli")      // entry-point for `Eugene`
    mainClass.set("jayden.app.Cli")         // entry-point for `Jayden`
    // mainClass.set("jayden.app.Gui")         // entry-point for `Jayden`
    // mainClass.set("noah.app.Cli")           // entry-point for `Noah`
















    /***************************************************************************
     * [Custom] Instructor Space
     ***************************************************************************/
    // mainClass.set("ydjs.app.Cli")        // entry-point for `Jaehoon (Instructor)`
    // mainClass.set("cs3510.app.Cli")         // Java-written class entry for CLI (Command Line Interface)
    // mainClass.set("cs3510.app.Gui")         // Java-written class entry for GUI (Graphical User Interface)
    // mainClass.set("cs3510.app.CliKt")         // Kotlin-written top-level entry for CLI (Command Line Interface)
    // mainClass.set("cs3510.app.GuiKt")       // Kotlin-written top-level entry with companion object main for GUI (Graphical User Interface)


    // mainClass.set("com.example.mvvm.App")
}










repositories {
    mavenCentral() // Dependencies Fetch (Maven Central) 
}

plugins {
    // [Gradle] 'init' generated
    id("ydjs.java-application-conventions")

    // [1] JVM extension: Kotlin
    kotlin("jvm") version "1.9.20"                  // Kotlin general language system (i.e. Kotlin compiler)
    id("org.jetbrains.dokka") version "1.9.20"      // KDoc, Kotlin documentation system: `https://github.com/Kotlin/dokka/releases`

    // [2] JavaFX (Java, special effects, FX)
    id("org.openjfx.javafxplugin") version "0.0.14" 
}

javafx {
    version = "21.0.5"          // Specify JavaFX version
    modules = listOf(           // Required JavaFX modules
        "javafx.controls", 
        "javafx.fxml"
    )
}

dependencies {
    // [Gradle] 'init' generated
    implementation("org.apache.commons:commons-text")
    implementation(project(":utilities"))

    // [1] JVM extension: Kotlin
    implementation(kotlin("stdlib"))

    // [2] JavaFX (Java, special effects, FX)
    implementation("org.openjfx:javafx-base:21.0.5")
    implementation("org.openjfx:javafx-controls:21.0.5")
    implementation("org.openjfx:javafx-fxml:21.0.5")
}























/***************************************************************************
 * [Custom] Gradle tasks.
 ***************************************************************************/
tasks.dokkaHtml {
    // `layout.buildDirectory`, making the build script more robust.
    outputDirectory.set(layout.buildDirectory.dir("docs/dokka")) // Specify where to output the documentation
    dokkaSourceSets {
        configureEach {
            includeNonPublic.set(false) // Only include public members
            skipDeprecated.set(false)  // Include deprecated elements
            reportUndocumented.set(true) // Warn about undocumented elements
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(
                    uri("https://github.com/your-repo/your-project/blob/main/src/main/kotlin").toURL()
                )
                remoteLineSuffix.set("#L")
            }
        }
    }
}


tasks.dokkaJavadoc {
    outputDirectory.set(layout.buildDirectory.dir("docs/javaxdoc")) // Specify where to output the documentation
    dokkaSourceSets {
        configureEach {
            includeNonPublic.set(false) // Only include public members
            skipDeprecated.set(false)  // Include deprecated elements
            reportUndocumented.set(true) // Warn about undocumented elements
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(
                    uri("https://github.com/your-repo/your-project/blob/main/src/main/kotlin").toURL()
                )
                remoteLineSuffix.set("#L")
            }
        }
    }
}

tasks.register("dokka") {
    group = "documentation" // Group for better organization in Gradle tasks
    description = "Generates both Dokka HTML and Javadoc documentation."

    dependsOn(tasks.dokkaHtml, tasks.dokkaJavadoc)

    doLast {
        println("Documentation tasks completed: dokkaHtml and dokkaJavadoc.")
    }
}











 // Define package names and class names as shared variables
// val packages = listOf(
//     "eugene.unit.f",
//     "jayden.unit.f",
//     "noah.unit.f",
//     "jayden.unit.f",
//     "ydjs.unit.f"
// )
val packages = listOf(
    "anderson.app"
)
val classNames = listOf(
    "Gui", 
    "Cli"
)
// repetition
// val classNames = mutableListOf<String>()
// for (i in 1..5) {
//     val suffix = i.toString().padStart(2, '0') // Ensures 01, 02, ..., 99
//     classNames.add("Exercise$suffix")
// }


// Register a Gradle task using the shared variables
tasks.register("setClass") {
    doLast {
        // Generate all combinations of package and class names
        val combinations = packages.flatMap { pkg ->
            classNames.map { className -> pkg to className }
        }

        combinations.forEach { (packageName, className) ->
            // Define the Java file path
            val packagePath = packageName.replace(".", File.separator)
            val javaFile = file("src/main/java/$packagePath/$className.java")

            // Create the Java file if it doesn't exist
            if (!javaFile.exists()) {
                createJavaFile(javaFile, packageName, className)
            }

            // Define the Test file path using the helper function
            val testFile = getTestFile(javaFile)

            // Create the Test file if it doesn't exist
            if (!testFile.exists()) {
                createTestFile(testFile, packageName, className)
            }
        }
    }
}

fun createJavaFile(javaFile: File, packageName: String, className: String) {
    javaFile.parentFile.mkdirs() // Create directories if needed
    javaFile.writeText("""
        package $packageName;

        public class $className {
            public static void main(String[] args) {
                System.out.println("Hello, $className!");
            }
        }
    """.trimIndent())
    println("Created: $javaFile")
}

fun getTestFile(javaFile: File): File {
    val testFilePath = "src/test/java/${javaFile.parentFile
        .relativeTo(file("src/main/java"))
        .path
        .replace(File.separator, "/")}/${javaFile.nameWithoutExtension}Test.java"
    return file(testFilePath)
}

fun createTestFile(testFile: File, packageName: String, className: String) {
    testFile.parentFile.mkdirs() // Create directories if needed
    testFile.writeText("""
        package $packageName;

        import org.junit.jupiter.api.Test;

        import static org.junit.jupiter.api.Assertions.*;

        public class ${className}Test {
            @Test
            public void testMainMethod() {
                String expectedOutput = "Hello, $className!";
                assertEquals(expectedOutput, "Hello, $className!");
            }
        }
    """.trimIndent())
    println("Created test file: $testFile")
}

// tasks.register("setClass") {
//     // Define file paths for Java and test files
//     val javaFiles = listOf(
//         "src/main/java/com/example/MyClass1.java",
//         "src/main/java/com/example/MyClass2.java",
//         "src/main/java/com/example/AnotherClass.java"
//     )

//     doLast {
//         javaFiles.forEach { path ->
//             val javaFile = file(path)
//             val packageName = javaFile.parentFile
//                 .relativeTo(file("src/main/java"))
//                 .path
//                 .replace(File.separator, ".")
//             val className = javaFile.nameWithoutExtension

//             // Create Java file if it doesn't exist
//             if (!javaFile.exists()) {
//                 createJavaFile(javaFile, packageName, className)
//             }

//             // Create test file if it doesn't exist
//             val testFile = getTestFile(javaFile)
//             if (!testFile.exists()) {
//                 createTestFile(testFile, packageName, className)
//             }
//         }
//     }
// }