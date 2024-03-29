import com.scalapenos.sbt.prompt.SbtPrompt.autoImport._
import com.scalapenos.sbt.prompt._
import Dependencies._

name := """fs2_launch"""

val format = taskKey[Unit]("Format files using scalafmt")

promptTheme := PromptTheme(
  List(
    text(_ => "[fs2_launch]", fg(64)).padRight(" λ ")
  )
)

lazy val testSettings: Seq[Def.Setting[_]] = List(
  Test / parallelExecution := false,
  publish / skip := true,
  fork := true
)

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false,
  publish / skip := true
)

lazy val `fs2_launch` = project
  .in(file("."))
  .settings(
    testSettings,
    organization := "asachdeva",
    name := "fs2_launch",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.9",
    libraryDependencies ++= Seq(
      Libraries.fs2Core,
      Libraries.fs2IO,
      Libraries.circeParser,
      Libraries.circeGeneric,
      Libraries.circeCore
    ),
    addCompilerPlugin(Libraries.betterMonadicFor),
    scalafmtOnCompile := true,
    format := {
      Command.process("scalafmtAll", state.value)
      Command.process("scalafmtSbt", state.value)
    }
  )

// CI build
addCommandAlias("buildfs2launch", ";clean;+test;")
