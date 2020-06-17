import sbt._

object Dependencies {

  object Versions {
    val circe = "0.13.0"
    val fs2 = "2.4.2"

    // Test

    // Compiler
    val kindProjector = "0.11.0"
    val betterMonadicFor = "0.3.1"

    // Runtime
    val logback = "1.2.3"
  }

  object Libraries {
    def circe(artifact: String, version: String): ModuleID = "io.circe" %% artifact % version
    def fs2(artifact: String, version: String): ModuleID = "co.fs2" %% artifact % version

    lazy val fs2Core = fs2("fs2-core", Versions.fs2)
    lazy val circeParser = circe("circe-parser", Versions.circe)

    // Compiler
    lazy val kindProjector = ("org.typelevel" %% "kind-projector" % Versions.kindProjector).cross(CrossVersion.full)
    lazy val betterMonadicFor = "com.olegpy" %% "better-monadic-for" % Versions.betterMonadicFor

    // Runtime
    lazy val logback = "ch.qos.logback" % "logback-classic" % Versions.logback

    // Test
  }

}
