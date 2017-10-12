logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.5")
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")
//addSbtPlugin("com.artima.supersafe" % "sbtplugin" % "1.1.2")
addSbtPlugin("com.iheart" % "sbt-play-swagger" % "0.6.1-PLAY2.6")
