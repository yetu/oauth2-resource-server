name := """sampleOAuth2ResourceServer"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  ws,
  "com.yetu" %% "oauth2-resource-server" % "0.1.2"
)

bintrayResolverSettings

resolvers += bintray.Opts.resolver.mavenRepo("yetu")
