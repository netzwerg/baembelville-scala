name := """baembelville"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  "com.typesafe.play" % "play-mailer_2.11" % "2.4.0"
)
