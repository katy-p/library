name := "library"

resolvers in ThisBuild += Resolver.jcenterRepo

val springVersion = "5.1.0.RELEASE"

libraryDependencies += "org.springframework" % "spring-webflux" % springVersion

libraryDependencies += "org.projectlombok" % "lombok" % "1.18.2" % Provided

val junitVersion = "5.3.1"

testOptions += Tests.Argument(jupiterTestFramework, "-q", "-v")

libraryDependencies ++= Seq(
  "org.junit.jupiter" % "junit-jupiter-api" % junitVersion % Test,
	"org.junit.jupiter" % "junit-jupiter-params" % junitVersion % Test,
	"org.junit.jupiter" % "junit-jupiter-engine" % junitVersion % Test
)