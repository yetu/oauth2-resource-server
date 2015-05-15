import play.PlayScala
import sbt.Keys._
import bintray.Keys._

name := """oauth2-resource-server"""

organization := "com.yetu"

scalaVersion :=  "2.11.2"

libraryDependencies ++= Seq(
  ws,
  "net.logstash.logback" % "logstash-logback-encoder" % "3.0",
  "com.nimbusds" % "nimbus-jose-jwt" % "3.10",
  "org.scalatestplus" %% "play" % "1.2.0" % "test",
  "com.softwaremill.macwire" %% "macros" % "0.7.1")

scalacOptions := Seq("-encoding", "utf8",
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-unchecked",
  "-deprecation",
  "-Xlog-reflective-calls",
  "-Ywarn-adapted-args"
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)

// scoverage plugin for test coverage:
instrumentSettings

// ----------- publishing settings -----------------------------------
// http://www.scala-sbt.org/0.13.5/docs/Detailed-Topics/Publishing.html
// -------------------------------------------------------------------

crossScalaVersions := Seq("2.10.4", "2.11.2")

// sbt-release plugin settings:
releaseSettings

publishMavenStyle := true

publishArtifact in (Test, packageBin) := true

// settings for bintray publishing

bintrayPublishSettings

repository in bintray := "maven"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

packageLabels in bintray := Seq("oauth2", "yetu")

bintrayOrganization in bintray := Some("yetu")


