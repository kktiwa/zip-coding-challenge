import Dependencies._

name := "zip-coding-challenge"
ThisBuild / organization := "au.com.zip"
ThisBuild / scalaVersion := "2.12.8"

lazy val commonSettings = Seq(
  libraryDependencies := dependencies
)

lazy val assemblySettings = Seq(
  assemblyMergeStrategy in assembly := {
    case PathList("mozilla", _@_*) => MergeStrategy.discard
    case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.discard
    case x => {
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
    }
  }
)

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    assemblySettings
  )

