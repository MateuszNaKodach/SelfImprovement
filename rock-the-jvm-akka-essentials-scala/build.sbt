name := "rock-the-jvm-akka-essentials-scala"

version := "0.1"

scalaVersion := "2.13.7"

idePackagePrefix := Some("pl.zycienakodach")

val akkaVersion = "2.6.17"
val scalaTestVersion = "3.2.9"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion,
)
