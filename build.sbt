name := "EmailService"

version := "0.1"

scalaVersion := "2.13.6"

val AkkaVersion = "2.6.15"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % Test
)

libraryDependencies += "org.apache.commons" % "commons-email" % "1.5"

libraryDependencies += "org.apache.kafka" %% "kafka" % "2.8.0"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.32"

val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)