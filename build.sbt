name := "Play2Test"

version := "1.0"

lazy val `play2test` = (project in file(".")).enablePlugins(PlayScala)
lazy val root = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin) //enable plugin

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

resolvers += "Flyway" at "https://flywaydb.org/repo"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  jdbc,
  ehcache,
  ws,
  specs2 % Test,
  guice,
  "com.github.dreamhead" % "moco-core" % "0.11.1",
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.flywaydb" % "flyway-core" % "4.2.0",
  "org.flywaydb" %% "flyway-play" % "4.0.0",
  "org.postgresql" % "postgresql" % "42.1.4",
  "org.webjars" % "swagger-ui" % "2.2.0"
)

swaggerDomainNameSpaces := Seq("models")

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")
