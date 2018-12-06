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
  "commons-io" % "commons-io" % "2.6" % Test,
  "com.opentable.components" % "otj-pg-embedded" % "0.1.1" % Test
)

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

val jacksonVersion = "2.9.7"

javacOptions += "-parameters"

// https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
libraryDependencies ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % jacksonVersion,
  "com.github.joschi.jackson" % "jackson-datatype-threetenbp" % jacksonVersion
)
