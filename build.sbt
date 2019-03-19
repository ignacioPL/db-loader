name := "akka-db-load"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "com.typesafe.akka"   %% "akka-stream" % "2.5.21"
libraryDependencies += "com.lightbend.akka"  %% "akka-stream-alpakka-slick" % "1.0-RC1"
libraryDependencies += "org.postgresql"      %  "postgresql"  % "42.2.5"
libraryDependencies += "com.danielasfregola" %% "random-data-generator" % "2.6"
