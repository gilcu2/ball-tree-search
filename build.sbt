import AcceptanceTest._
import IntegrationTest._
import UnitTest._

organization := "com.gilcu2"

UnitTestSettings ++ IntegrationTestSettings ++ AcceptanceTestSettings
lazy val TestAll: Configuration = config("test-all").extend(AcceptanceTest.AcceptanceTestConfig)
configs(IntegrationTestConfig, AcceptanceTestConfig, TestAll)

name := "ball-tree-search"

version := "0.1"

scalaVersion := "2.12.10"

resolvers += Resolver.bintrayRepo("cibotech", "public")
resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(

  "org.creativescala" %% "doodle" % "0.9.9",
  "com.cibo" %% "evilplot" % "0.6.3",
  "com.github.darrenjw" %% "scala-view" % "0.6-SNAPSHOT",

  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

// Determine OS version of JavaFX binaries
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

// Add dependency on JavaFX libraries, OS dependent
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map(m =>
  "org.openjfx" % s"javafx-$m" % "12.0.2" classifier osName
)
