name := "firstSlick"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.2",
  "org.slf4j" % "slf4j-nop" % "1.7.29",
  "org.postgresql" % "postgresql" % "42.2.8",
  "mysql" % "mysql-connector-java" % "8.0.18",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
  "ch.qos.logback"      % "logback-classic" % "1.2.3",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)