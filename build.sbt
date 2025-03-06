name := """contact-management"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.16"

libraryDependencies ++= Seq(
  guice,
  jdbc,
  "org.playframework.anorm" %% "anorm" % "2.7.0",
  "com.typesafe.slick" %% "slick" % "3.4.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1",
  "org.xerial" % "sqlite-jdbc" % "3.45.1.0",
  "com.typesafe.play" %% "play-slick" % "5.1.0",
  "com.typesafe.play" %% "twirl-api" % "1.5.1",
)

dependencyOverrides += "org.scala-lang.modules" %% "scala-xml" % "1.2.0"

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
