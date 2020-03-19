name := "akka-stream-playground"
ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "1.0.0-SNAPSHOT"
ThisBuild / organization := "com.github.daggerok"
ThisBuild / licenses := Seq(("MIT", url("https://github.com/daggerok/akka-stream-playground/blob/master/LICENSE")))
//libraryDependencies in ThisBuild ++= scalaProjectDependencies

/* def projects, starts from root */

lazy val root =
  (project in file("."))
    .dependsOn(deps: _*)
    .aggregate(refs: _*)
    .settings(
      update / aggregate := false,
    )

/* DRY */

lazy val deps: Array[ClasspathDep[ProjectReference]] =
  refs.map(ClasspathDependency(_, Option.empty))

lazy val refs = Array[ProjectReference](
  template,
  `typed-akka-production-ready-api-java-example`,
  `typed-akka-production-ready-api-scala-example`,
)

/* def subProjects */

lazy val template =
  (project in file("template"))
    .settings(
      libraryDependencies ++= akkaStreamProjectDependencies,
      mainClass in (Compile, run) := Some("daggerok.Main"),
      mainClass in assembly := Some("daggerok.Main"),
    )

lazy val `typed-akka-production-ready-api-scala-example` =
  (project in file("typed/typed-akka-production-ready-api-scala-example"))
    .settings(
      libraryDependencies ++= typedAkkaProjectDependencies,
      mainClass in (Compile, run) := Some("daggerok.Main"),
      mainClass in assembly := Some("daggerok.Main"),
    )

lazy val `typed-akka-production-ready-api-java-example` =
  (project in file("typed/typed-akka-production-ready-api-java-example"))
    .settings(
      libraryDependencies ++= (
        typedAkkaProjectDependencies ++
        Seq(
          "org.projectlombok" % "lombok" % "1.18.12" % "provided"
        )
      ),
      mainClass in (Compile, run) := Some("daggerok.Main"),
      mainClass in assembly := Some("daggerok.Main"),
    )

/* versions */

lazy val akkaVersion = "2.6.4"
lazy val scalatestVersion = "3.1.1"
lazy val slf4jVersion = "1.7.30"

/* dependencies */

lazy val akkaStreamProjectDependencies = Seq(
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "org.scalactic" %% "scalactic" % scalatestVersion % Test,
  "org.scalatest" %% "scalatest" % scalatestVersion % Test,
)

lazy val typedAkkaProjectDependencies = Seq(
  "org.slf4j" % "slf4j-simple" % slf4jVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
)
