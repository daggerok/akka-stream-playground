name := "akka-stream-playground"
ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "1.0.0-SNAPSHOT"
ThisBuild / organization := "com.github.daggerok"
ThisBuild / licenses := Seq(("MIT", url("https://github.com/daggerok/akka-stream-playground/blob/master/LICENSE")))
//libraryDependencies in ThisBuild ++= scalaProjectDependencies

/* def projects, starts from root */

lazy val root =
  (project in file("."))
    .aggregate(refs: _*)
    .dependsOn(deps: _*)
    .settings(
      //commonSettings,
      update / aggregate := false,
    )

/* DRY */

lazy val refs = Array[ProjectReference](
 template,
)

lazy val deps: Array[ClasspathDep[ProjectReference]] =
  refs.map(ClasspathDependency(_, Option.empty))

/* def subProjects */

lazy val template =
  (project in file("template"))
    .settings(
      libraryDependencies ++= scalaProjectDependencies,
      mainClass in (Compile, run) := Some("daggerok.Main"),
      mainClass in assembly := Some("daggerok.Main"),
    )

/* versions */

lazy val akkaVersion = "2.6.4"
lazy val scalatestVersion = "3.1.1"

/* dependencies */

lazy val scalaProjectDependencies = Seq(
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "org.scalactic" %% "scalactic" % scalatestVersion % Test,
  "org.scalatest" %% "scalatest" % scalatestVersion % Test,
)
