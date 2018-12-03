name := "library"

resolvers in ThisBuild += Resolver.jcenterRepo

val springVersion = "5.1.0.RELEASE"

libraryDependencies += "org.springframework" % "spring-webflux" % springVersion

libraryDependencies += "org.projectlombok" % "lombok" % "1.18.2" % Provided

val junitVersion = "5.3.1"

testOptions += Tests.Argument(jupiterTestFramework, "-v")

libraryDependencies ++= Seq(
  "org.junit.jupiter" % "junit-jupiter-api" % junitVersion % Test,
	"org.junit.jupiter" % "junit-jupiter-params" % junitVersion % Test,
	"org.junit.jupiter" % "junit-jupiter-engine" % junitVersion % Test,
	"org.mockito" % "mockito-core" % "2.7.22" % Test,
  "org.apache.httpcomponents" % "httpclient" % "4.5.6" % Test,
  "commons-io" % "commons-io" % "2.6" % Test
)

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)