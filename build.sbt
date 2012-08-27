name := "specs2-examples"

organization := "org.specs2"

version := "1.0"

scalaVersion := "2.9.2"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.12.1" % "test",
  "org.mockito" % "mockito-all" % "1.9.0" % "test",
  "org.scalacheck" %% "scalacheck" % "1.9"
)

initialCommands := "import org.specs2.examples._"
