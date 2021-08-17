name := "EmailService"

version := "0.1"

scalaVersion := "2.13.6"

val AkkaVersion = "2.6.15"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % Test
)

libraryDependencies += "org.apache.commons" % "commons-email" % "1.5"

lazy val kafkaClientsDeps: List[ModuleID] =
  "org.apache.kafka" % "kafka-clients" % "2.6.0" ::
    "io.confluent" % "kafka-avro-serializer" % "6.0.0" :: Nil