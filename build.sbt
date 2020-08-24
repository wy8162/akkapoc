name := "akka-poc"

version := "1.0"

scalaVersion := "2.13.1"

lazy val akkaVersion = "2.6.8"

organization := "y.w"
description := "Customer Accounts API POC"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.springframework.boot" % "spring-boot-starter-web" % "2.3.3.RELEASE",
  "org.projectlombok" % "lombok" % "1.18.12" % "provided",
  "org.springframework.cloud" % "spring-cloud-starter-circuitbreaker-resilience4j" % "1.0.4.RELEASE",
  "org.springframework.boot" % "spring-boot-starter-test" % "2.3.3.RELEASE" % Test,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test
)

/*
  Packaging plugin
 */

enablePlugins(JavaAppPackaging)

// set the main entrypoint to the application that is used in startup scripts
mainClass in Compile := Some("y.w.c1.AkkapocApplication")
