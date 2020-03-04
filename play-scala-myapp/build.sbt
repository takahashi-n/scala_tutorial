name := """play-scala-myapp"""
organization := "com.tuyano,play"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "org.playframework.anorm" %% "anorm" % "2.6.5"
libraryDependencies += "com.h2database" % "h2" % "1.4.197"
libraryDependencies += evolutions
libraryDependencies += jdbc

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.tuyano,play.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.tuyano,play.binders._"
