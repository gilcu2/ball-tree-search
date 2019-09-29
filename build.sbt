import AcceptanceTest._
import IntegrationTest._
import UnitTest._

organization := "com.gilcu2"

UnitTestSettings ++ IntegrationTestSettings ++ AcceptanceTestSettings
lazy val TestAll: Configuration = config("test-all").extend(AcceptanceTest.AcceptanceTestConfig)
configs(IntegrationTestConfig, AcceptanceTestConfig, TestAll)

name := "ball-tree-search"

version := "0.1"

scalaVersion := "2.12.8"

resolvers += Resolver.bintrayRepo("cibotech", "public")
resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(

  "org.creativescala" %% "doodle" % "0.9.7",
  "com.cibo" %% "evilplot" % "0.6.3",
  "com.github.darrenjw" %% "scala-view" % "0.6-SNAPSHOT",

  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)
